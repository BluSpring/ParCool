package com.alrex.parcool.common.handlers;

import com.alrex.parcool.common.action.impl.HideInBlock;
import com.alrex.parcool.common.attachment.common.Parkourability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerVisibilityHandler {
    public static double onLivingVisibilityEvent(LivingEntity entity, double original) {
        if (entity instanceof Player player) {
            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return original;
            if (parkourability.get(HideInBlock.class).isDoing()) {
                return original * (original * 0.1);
            }
        }

        return original;
    }
}
