package com.alrex.parcool.common.registries;

import com.alrex.parcool.client.hud.HUDManager;
import com.alrex.parcool.client.input.KeyRecorder;
import com.alrex.parcool.common.action.ActionProcessor;
import com.alrex.parcool.common.handlers.*;
import com.alrex.parcool.common.potion.ParCoolBrewingRecipe;

public class EventBusForgeRegistry {
	public static void register() {
        ParCoolBrewingRecipe.init();
//        bus.register(PlayerJumpHandler.class); // LivingEntityMixin
        LoginLogoutHandler.init();
//        bus.register(PlayerVisibilityHandler.class); // LivingEntityMixin
        PlayerDamageHandler.init();
        PlayerCloneHandler.init();
		ActionProcessor.INSTANCE.init();
	}

	public static void registerClient() {
//        bus.register(KeyRecorder.class); // LocalPlayerMixin
        OpenSettingsParCoolHandler.init();
        EnableOrDisableParCoolHandler.init();
        PlayerJoinHandler.init();
        HUDManager.getInstance();
        InputHandler.init();
        ActionProcessor.ClientActionProcessor.INSTANCE.init();
	}
}
