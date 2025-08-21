package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.attachment.common.Parkourability;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerCloneHandler {
    public static void init() {
        PlayerEvents.Clone.EVENT.register(PlayerCloneHandler::onClone);
    }

	public static void onClone(PlayerEvents.Clone event) {
        Player player = event.getEntity();
        if (event.isWasDeath() && player instanceof ServerPlayer) {
			Player from = event.getOriginal();
			Parkourability pFrom = Parkourability.get(from);
            Parkourability pTo = Parkourability.get(player);
			if (pFrom != null && pTo != null) {
				pTo.CopyFrom(pFrom);
			}
		}
	}
}
