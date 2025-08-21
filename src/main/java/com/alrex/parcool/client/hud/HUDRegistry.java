package com.alrex.parcool.client.hud;

public class HUDRegistry {
    public static void init() {
        onRegisterGui();
    }

    public static void onRegisterGui() {
        HUDManager.getInstance().registerHUD();
    }
}
