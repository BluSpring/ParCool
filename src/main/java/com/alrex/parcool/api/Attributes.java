package com.alrex.parcool.api;

import com.alrex.parcool.ParCool;
import io.github.fabricators_of_create.porting_lib.registry.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class Attributes {
    private static boolean hasRegistered = false;

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ParCool.MOD_ID);
    public static final DeferredHolder<Attribute, Attribute> MAX_STAMINA = ATTRIBUTES.register("max_stamina", () -> new RangedAttribute("parcool.max_stamina", 2000, 10, 10000).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> STAMINA_RECOVERY = ATTRIBUTES.register("stamina_recovery", () -> new RangedAttribute("parcool.stamina_recovery", 20, 0, 10000).setSyncable(true));

    public static void registerAll() {
        if (hasRegistered)
            return;

        hasRegistered = true;
        ATTRIBUTES.register();
    }
}
