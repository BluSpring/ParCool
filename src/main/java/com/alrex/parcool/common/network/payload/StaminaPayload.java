package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.Attachments;
import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
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

import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public record StaminaPayload(UUID playerID, ReadonlyStamina stamina) implements CustomPacketPayload {
    public static final Type<StaminaPayload> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.stamina"));
    public static final StreamCodec<ByteBuf, StaminaPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            (s) -> s.playerID().getMostSignificantBits(),
            ByteBufCodecs.VAR_LONG,
            (s) -> s.playerID().getLeastSignificantBits(),
            ReadonlyStamina.STREAM_CODEC,
            StaminaPayload::stamina,
            (ms, ls, s) -> new StaminaPayload(new UUID(ms, ls), s)
    );

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(StaminaPayload payload, ClientPlayNetworking.Context context) {
        Player player = context.player().level().getPlayerByUUID(payload.playerID);
        context.client().execute(() -> {
            if (player == null || player.isLocalPlayer()) return;
            player.setAttached(Attachments.STAMINA, payload.stamina);
        });
    }

    public static void handleServer(StaminaPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            Player player = context.player().level().getPlayerByUUID(payload.playerID);
            if (player == null) return;
            for (ServerPlayer serverPlayer : PlayerLookup.all(context.server())) {
                ServerPlayNetworking.send(serverPlayer, payload);
            }
            player.setAttached(Attachments.STAMINA, payload.stamina);
        });
    }
}
