package com.alrex.parcool.common.network;

import com.alrex.parcool.common.network.payload.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkRegistries {
    private static final String PROTOCOL_VERSION = "3.3.0.0";

    public static void onRegisterPayload() {
        PayloadTypeRegistry.playC2S().register(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleServer);
        PayloadTypeRegistry.playC2S().register(ActionStatePayload.TYPE, ActionStatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleServer);
        PayloadTypeRegistry.playC2S().register(LimitationPayload.TYPE, LimitationPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleServer);
        PayloadTypeRegistry.playC2S().register(ClientInformationPayload.TYPE, ClientInformationPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleServer);
        PayloadTypeRegistry.playC2S().register(StaminaPayload.TYPE, StaminaPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleServer);
        PayloadTypeRegistry.playC2S().register(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleServer);
    }

    public static void onRegisterClientPayloads() {
        PayloadTypeRegistry.playS2C().register(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(StartBreakfallEventPayload.TYPE, StartBreakfallEventPayload::handleClient);
        PayloadTypeRegistry.playS2C().register(ActionStatePayload.TYPE, ActionStatePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ActionStatePayload.TYPE, ActionStatePayload::handleClient);
        PayloadTypeRegistry.playS2C().register(LimitationPayload.TYPE, LimitationPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(LimitationPayload.TYPE, LimitationPayload::handleClient);
        PayloadTypeRegistry.playS2C().register(ClientInformationPayload.TYPE, ClientInformationPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ClientInformationPayload.TYPE, ClientInformationPayload::handleClient);
        PayloadTypeRegistry.playS2C().register(StaminaPayload.TYPE, StaminaPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(StaminaPayload.TYPE, StaminaPayload::handleClient);
        PayloadTypeRegistry.playS2C().register(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(StaminaProcessOnServerPayload.TYPE, StaminaProcessOnServerPayload::handleClient);
    }
}
