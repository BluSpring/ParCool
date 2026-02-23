package com.alrex.parcool.common.network;

import com.alrex.parcool.common.network.payload.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkRegistries {
    private static final String PROTOCOL_VERSION = "3.3.0.0";

    public static void onRegisterPayload() {
        PayloadTypeRegistry.playC2S().register(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ActionStatePayload.TYPE, ActionStatePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(LimitationPayload.TYPE, LimitationPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ClientInformationPayload.TYPE, ClientInformationPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(StaminaPayload.TYPE, StaminaPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ActionStatePayload.TYPE, ActionStatePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(LimitationPayload.TYPE, LimitationPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ClientInformationPayload.TYPE, ClientInformationPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(StaminaPayload.TYPE, StaminaPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleServer);
        ServerPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleServer);
    }

    public static void onRegisterClientPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleClient);
        ClientPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleClient);
    }
}
