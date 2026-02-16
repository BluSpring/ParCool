package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.attachment.common.Parkourability;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerCloneHandler {
	public static void init() {
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			onClone(oldPlayer, newPlayer, !alive);
		});
	}

	public static void onClone(Player from, Player player, boolean wasDeath) {
        if (wasDeath && player instanceof ServerPlayer) {
			Parkourability pFrom = Parkourability.get(from);
            Parkourability pTo = Parkourability.get(player);
			if (pFrom != null && pTo != null) {
				pTo.CopyFrom(pFrom);
			}
		}
	}
}
