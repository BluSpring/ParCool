package com.alrex.parcool.client;

import com.alrex.parcool.client.hud.HUDRegistry;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.client.renderer.Renderers;
import com.alrex.parcool.common.attachment.ClientAttachments;
import com.alrex.parcool.common.network.NetworkRegistries;
import com.alrex.parcool.common.registries.EventBusForgeRegistry;
import net.fabricmc.api.ClientModInitializer;

public class ParCoolClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkRegistries.onRegisterClientPayloads();
        HUDRegistry.onRegisterGui();
        EventBusForgeRegistry.registerClient();
        KeyBindings.register();
        Renderers.register();
        ClientAttachments.registerAll();
    }
}
