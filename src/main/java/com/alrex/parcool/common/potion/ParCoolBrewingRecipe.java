package com.alrex.parcool.common.potion;


import io.github.fabricators_of_create.porting_lib.brewing.RegisterBrewingRecipesEvent;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class ParCoolBrewingRecipe {
    public static void init() {
        RegisterBrewingRecipesEvent.EVENT.register(ParCoolBrewingRecipe::onRegister);
    }

    public static void onRegister(RegisterBrewingRecipesEvent event) {
        event.getBuilder()
                .addMix(
                        Potions.AWKWARD,
                        Items.POISONOUS_POTATO,
                        com.alrex.parcool.common.potion.Potions.POOR_ENERGY_DRINK
                );
        event.getBuilder()
                .addMix(
                        Potions.AWKWARD,
                        Items.CHICKEN,
                        com.alrex.parcool.common.potion.Potions.POOR_ENERGY_DRINK
                );
        event.getBuilder()
                .addMix(
                        Potions.AWKWARD,
                        Items.QUARTZ,
                        com.alrex.parcool.common.potion.Potions.ENERGY_DRINK
                );
        event.getBuilder()
                .addMix(
                        com.alrex.parcool.common.potion.Potions.POOR_ENERGY_DRINK,
                        Items.QUARTZ,
                        com.alrex.parcool.common.potion.Potions.ENERGY_DRINK
                );
	}
}
