package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.info.ClientSetting;
import com.alrex.parcool.common.network.payload.ClientInformationPayload;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityJoinLevelEvent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerJoinHandler {
    public static void init() {
        EntityJoinLevelEvent.EVENT.register(PlayerJoinHandler::onPlayerJoin);
    }

    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) return;
        Entity entity = event.getEntity();
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
