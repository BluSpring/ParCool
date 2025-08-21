package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.info.ClientSetting;
import com.alrex.parcool.server.limitation.Limitations;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public record ClientInformationPayload(UUID playerID, boolean requestLimitation,
                                       ClientSetting information) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientInformationPayload> TYPE
            = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.client_info"));
    public static final StreamCodec<ByteBuf, ClientInformationPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            (p) -> p.playerID().getMostSignificantBits(),
            ByteBufCodecs.VAR_LONG,
            (p) -> p.playerID().getLeastSignificantBits(),
            ByteBufCodecs.BOOL,
            ClientInformationPayload::requestLimitation,
            ClientSetting.STREAM_CODEC,
            ClientInformationPayload::information,
            (ms, ls, r, i) -> new ClientInformationPayload(new UUID(ms, ls), r, i)
    );

    @NotNull
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(ClientInformationPayload payload, ClientPlayNetworking.Context context) {
        Level world = context.player().level();
        context.client().execute(() -> {
            var player = world.getPlayerByUUID(payload.playerID());
            if (player == null || player.isLocalPlayer()) return;
            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;
            parkourability.getActionInfo().setClientSetting(payload.information());
        });
    }

    public static void handleServer(ClientInformationPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            Player player = context.player();
            for (ServerPlayer serverPlayer : PlayerLookup.all(context.server())) {
                ServerPlayNetworking.send(serverPlayer, payload);
            }

            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;
            if (player instanceof ServerPlayer serverPlayer && payload.requestLimitation()) {
                Limitations.update(serverPlayer);
            }
            parkourability.getActionInfo().setClientSetting(payload.information());
        });
    }
}
