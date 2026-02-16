package com.alrex.parcool.common.stamina.handlers;

import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import com.alrex.parcool.common.stamina.IParCoolStaminaHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class InfiniteStaminaHandler implements IParCoolStaminaHandler {
    private static final ReadonlyStamina INSTANCE = new ReadonlyStamina(false, 1, 1);

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina initializeStamina(Player player, ReadonlyStamina current) {
        return INSTANCE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina consume(Player player, ReadonlyStamina current, int value) {
        return INSTANCE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina recover(Player player, ReadonlyStamina current, int value) {
        return INSTANCE;
    }
}
