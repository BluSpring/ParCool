package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.action.impl.BreakfallReady;
import com.alrex.parcool.common.attachment.common.Parkourability;
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

public record StartBreakfallEventPayload(boolean justTimed) implements CustomPacketPayload {
    public static final Type<StartBreakfallEventPayload> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.start_breakfall_event"));
    public static final StreamCodec<ByteBuf, StartBreakfallEventPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            StartBreakfallEventPayload::justTimed,
            StartBreakfallEventPayload::new
    );

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(StartBreakfallEventPayload payload, ClientPlayNetworking.Context context) {
        Player player = context.player();

        context.client().execute(() -> {
            Parkourability parkourability = Parkourability.get(player);
            parkourability.get(BreakfallReady.class).startBreakfall(player, parkourability, payload.justTimed());
        });
    }

    public static void handleServer(StartBreakfallEventPayload payload, ServerPlayNetworking.Context context) {
        throw new UnsupportedOperationException("This should have been designed not to be called");
    }
}
