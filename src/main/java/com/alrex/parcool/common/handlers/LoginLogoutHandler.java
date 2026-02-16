package com.alrex.parcool.common.handlers;

import com.alrex.parcool.server.limitation.Limitations;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LoginLogoutHandler {
    public static void init() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            onLogoutInServer(handler.player);
        });
    }

    public static void onLogoutInServer(Player player) {
        if (player instanceof ServerPlayer) {
            Limitations.unload(player.getUUID());
        }
    }
}
