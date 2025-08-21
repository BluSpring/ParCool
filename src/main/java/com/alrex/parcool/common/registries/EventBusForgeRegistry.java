package com.alrex.parcool.common.registries;

import com.alrex.parcool.client.hud.HUDManager;
import com.alrex.parcool.client.input.KeyRecorder;
import com.alrex.parcool.common.action.ActionProcessor;
import com.alrex.parcool.common.handlers.*;
import com.alrex.parcool.common.potion.ParCoolBrewingRecipe;

public class EventBusForgeRegistry {
	public static void register() {
        ParCoolBrewingRecipe.init();
        PlayerJumpHandler.init();
        LoginLogoutHandler.init();
        PlayerVisibilityHandler.init();
        PlayerDamageHandler.init();
        PlayerCloneHandler.init();
        new ActionProcessor();
	}

	public static void registerClient() {
        KeyRecorder.init();
        OpenSettingsParCoolHandler.init();
        EnableOrDisableParCoolHandler.init();
        PlayerJoinHandler.init();
        HUDManager.getInstance();
        InputHandler.init();
	}
}
