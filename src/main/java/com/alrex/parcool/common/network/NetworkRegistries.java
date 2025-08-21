package com.alrex.parcool.common.network;

import com.alrex.parcool.common.network.payload.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkRegistries {
    private static final String PROTOCOL_VERSION = "3.3.0.0";

    public static void init() {
        onRegisterPayload();

        ServerPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleServer);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initClient();
        }
    }
    
    private static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleClient);
    }
    
    private static <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        PayloadTypeRegistry.playC2S().register(type, streamCodec);
        PayloadTypeRegistry.playS2C().register(type, streamCodec);
    }
    
    public static void onRegisterPayload() {
        playBidirectional(
                StartBreakfallEventPayload.TYPE,
                StartBreakfallEventPayload.CODEC
        );
        playBidirectional(
                ActionStatePayload.TYPE,
                ActionStatePayload.CODEC
        );
        playBidirectional(
                LimitationPayload.TYPE,
                LimitationPayload.CODEC
        );
        playBidirectional(
                ClientInformationPayload.TYPE,
                ClientInformationPayload.CODEC
        );
        playBidirectional(
                StaminaPayload.TYPE,
                StaminaPayload.CODEC
        );
        playBidirectional(
                StaminaProcessOnServerPayload.TYPE,
                StaminaProcessOnServerPayload.CODEC
        );
    }
}
