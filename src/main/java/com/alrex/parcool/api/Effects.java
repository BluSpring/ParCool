package com.alrex.parcool.api;


import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.potion.effects.InexhaustibleEffect;
import io.github.fabricators_of_create.porting_lib.registry.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class Effects {
	private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ParCool.MOD_ID);
	public static final DeferredHolder<MobEffect, MobEffect> INEXHAUSTIBLE = EFFECTS.register(
			"inexhaustible", InexhaustibleEffect::new
	);

	public static void registerAll() {
		EFFECTS.register();
	}
}
