package com.alrex.parcool.api;


import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.potion.effects.InexhaustibleEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class Effects {
	public static final Holder<MobEffect> INEXHAUSTIBLE = register(
			"inexhaustible", InexhaustibleEffect::new
	);

	private static Holder<MobEffect> register(String name, Supplier<MobEffect> effectSupplier) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ParCool.id(name), effectSupplier.get());
	}

	public static void registerAll() {
	}
}
