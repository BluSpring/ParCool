package com.alrex.parcool.api;

import com.alrex.parcool.ParCool;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.Supplier;

public class Attributes {
    public static final Holder<Attribute> MAX_STAMINA = register("max_stamina", () -> new RangedAttribute("parcool.max_stamina", 2000, 10, 10000).setSyncable(true));
    public static final Holder<Attribute> STAMINA_RECOVERY = register("stamina_recovery", () -> new RangedAttribute("parcool.stamina_recovery", 20, 0, 10000).setSyncable(true));

    private static <T extends Attribute> Holder<Attribute> register(String path, Supplier<T> attributeSupplier) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ParCool.id(path), attributeSupplier.get());
    }

    public static void registerAll() {
    }
}
