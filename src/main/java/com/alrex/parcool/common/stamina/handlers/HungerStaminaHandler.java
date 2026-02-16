package com.alrex.parcool.common.stamina.handlers;

import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import com.alrex.parcool.common.network.payload.StaminaProcessOnServerPayload;
import com.alrex.parcool.common.stamina.IParCoolStaminaHandler;
import com.alrex.parcool.common.stamina.StaminaType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HungerStaminaHandler implements IParCoolStaminaHandler {
    private int consumed = 0;

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina initializeStamina(Player player, ReadonlyStamina current) {
        return new ReadonlyStamina(false, player.getFoodData().getFoodLevel(), 20);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina consume(Player player, ReadonlyStamina current, int value) {
        consumed += value;
        return current;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina recover(Player player, ReadonlyStamina current, int value) {
        return current;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ReadonlyStamina onTick(Player player, ReadonlyStamina current) {
        if (consumed > 0) {
            ClientPlayNetworking.send(new StaminaProcessOnServerPayload(StaminaType.HUNGER, consumed));
            consumed = 0;
        }
        return new ReadonlyStamina(
                player.getFoodData().getFoodLevel() < 6,
                player.getFoodData().getFoodLevel(),
                20
        );
    }

    @Override
    public void processOnServer(Player player, int value) {
        player.causeFoodExhaustion(value / 1000f);
    }
}
