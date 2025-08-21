package com.alrex.parcool.client.hud;

import com.alrex.parcool.client.hud.impl.StaminaHUDController;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class HUDManager {
    private static HUDManager instance = null;

    private final StaminaHUDController staminaHUD = new StaminaHUDController();

    public static HUDManager getInstance() {
        if (instance == null) instance = new HUDManager();
        return instance;
    }

    public HUDManager() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onSetup() {
    }

    public void registerHUD() {
        HudRenderCallback.EVENT.register(staminaHUD::render);
    }

    public void onTick(Minecraft client) {
        staminaHUD.onTick(client);
    }
}
