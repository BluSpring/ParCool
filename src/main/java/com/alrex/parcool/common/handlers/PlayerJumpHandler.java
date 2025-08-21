package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.action.impl.ChargeJump;
import com.alrex.parcool.common.action.impl.Dive;
import com.alrex.parcool.common.action.impl.Flipping;
import com.alrex.parcool.common.attachment.common.Parkourability;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEvents;
import net.minecraft.world.entity.player.Player;

public class PlayerJumpHandler {
    public static void init() {
        LivingEvents.LivingJumpEvent.EVENT.register(PlayerJumpHandler::onJump);
    }

	public static void onJump(LivingEvents.LivingJumpEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		Parkourability parkourability = Parkourability.get(player);
		if (parkourability == null) return;
		parkourability.getAdditionalProperties().onJump();
		if (!player.isLocalPlayer()) return;
		parkourability.get(Dive.class).onJump(player, parkourability);
		parkourability.get(Flipping.class).onJump(player, parkourability);
		parkourability.get(ChargeJump.class).onJump(player, parkourability);
	}
}
