package com.alrex.parcool.client.hud;

import com.alrex.parcool.client.hud.impl.StaminaHUDController;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class HUDManager {
    private static HUDManager instance = null;

    private final StaminaHUDController staminaHUD = new StaminaHUDController();

    public static HUDManager getInstance() {
        if (instance == null) instance = new HUDManager();
        return instance;
    }

    public void onSetup() {
    }

    public void registerHUD() {
        HudElementRegistry.attachElementBefore(VanillaHudElements.FOOD_BAR, StaminaHUDController.ID, staminaHUD);
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(Minecraft client) {
        staminaHUD.onTick(client);
    }
}
