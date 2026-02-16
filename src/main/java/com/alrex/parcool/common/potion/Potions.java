package com.alrex.parcool.common.potion;

import com.alrex.parcool.ParCool;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class Potions {
    public static final Holder<Potion> POOR_ENERGY_DRINK =
			register(
					"poor_energy_drink",
					() -> new Potion(
							"poor_energy_drink",
                            new MobEffectInstance(com.alrex.parcool.api.Effects.INEXHAUSTIBLE, 2400/*2 min*/),
							new MobEffectInstance(MobEffects.HUNGER, 100),
							new MobEffectInstance(MobEffects.POISON, 100)
					)
			);
    public static final Holder<Potion> ENERGY_DRINK =
			register(
					"energy_drink",
					() -> new Potion(
							"energy_drink",
                            new MobEffectInstance(com.alrex.parcool.api.Effects.INEXHAUSTIBLE, 9600/*8 min*/)
					)
			);

	private static Holder<Potion> register(String name, Supplier<Potion> potionSupplier) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, ParCool.id(name), potionSupplier.get());
	}

	public static void registerAll() {
	}
}
