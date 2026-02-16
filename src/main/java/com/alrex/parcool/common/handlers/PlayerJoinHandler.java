package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.info.ClientSetting;
import com.alrex.parcool.common.network.payload.ClientInformationPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerJoinHandler {
    public static void init() {
        ClientEntityEvents.ENTITY_LOAD.register(PlayerJoinHandler::onPlayerJoin);
    }

    public static void onPlayerJoin(Entity entity, Level level) {
        if (!level.isClientSide()) return;
        if (entity instanceof Player player) {
            if (player instanceof LocalPlayer) {
                Parkourability parkourability = Parkourability.get(player);
                if (parkourability == null) return;
                parkourability.getActionInfo().setClientSetting(ClientSetting.readFromLocalConfig());
                ClientPlayNetworking.send(new ClientInformationPayload(player.getUUID(), true, parkourability.getClientInfo()));
            }
        }
    }
}
