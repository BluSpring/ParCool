package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.action.impl.HideInBlock;
import com.alrex.parcool.common.attachment.common.Parkourability;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerVisibilityHandler {
    public static void init() {
        LivingEvents.LivingVisibilityEvent.EVENT.register(PlayerVisibilityHandler::onLivingVisibilityEvent);
    }

    public static void onLivingVisibilityEvent(LivingEvents.LivingVisibilityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;
            if (parkourability.get(HideInBlock.class).isDoing()) {
                event.modifyVisibility(event.getVisibilityModifier() * 0.1);
            }
        }

    }
}
