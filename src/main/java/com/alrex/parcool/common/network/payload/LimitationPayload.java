package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.client.LocalStamina;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.info.ServerLimitation;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public record LimitationPayload(ServerLimitation limitation) implements CustomPacketPayload {
    public static final Type<LimitationPayload> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.limitation"));
    public static final StreamCodec<ByteBuf, LimitationPayload> CODEC = StreamCodec.composite(
            ServerLimitation.STREAM_CODEC,
            LimitationPayload::limitation,
            LimitationPayload::new
    );

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(LimitationPayload payload, ClientPlayNetworking.Context context) {
        var player = context.player();
        context.client().execute(() -> {
            Parkourability parkourability = Parkourability.get(player);
            parkourability.getActionInfo().setServerLimitation(payload.limitation());
            if (player instanceof LocalPlayer localPlayer) {
                parkourability.getActionInfo().updateStaminaType(LocalStamina.get(localPlayer), localPlayer);
            }
        });
    }

    public static void handleServer(LimitationPayload payload, ServerPlayNetworking.Context context) {
        throw new UnsupportedOperationException("This should have been designed not to be called");
    }
}
