package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.stamina.StaminaType;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;

public record StaminaProcessOnServerPayload(StaminaType stamina, int value) implements CustomPacketPayload {
    public static final Type<StaminaProcessOnServerPayload> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.custom_stamina"));
    public static final StreamCodec<ByteBuf, StaminaProcessOnServerPayload> CODEC = StreamCodec.composite(
            StaminaType.STREAM_CODEC,
            StaminaProcessOnServerPayload::stamina,
            ByteBufCodecs.VAR_INT,
            StaminaProcessOnServerPayload::value,
            StaminaProcessOnServerPayload::new
    );

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(StaminaProcessOnServerPayload payload, ClientPlayNetworking.Context context) {
        throw new UnsupportedOperationException("This should have been designed not to be called");
    }

    public static void handleServer(StaminaProcessOnServerPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            Player player = context.player();
            payload.stamina().newHandler(player).processOnServer(player, payload.value());
        });
    }
}
