package com.alrex.parcool.server.command.args;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.mixin.common.fabric.ArgumentTypeInfosAccessor;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class ParCoolArgumentTypeInfos {
    private static final Holder<ArgumentTypeInfo<?, ?>> ACTION_ARGUMENT_TYPE = register("action", () -> registerByClass(ActionArgumentType.class, SingletonArgumentInfo.contextFree(ActionArgumentType::action)));
    private static final Holder<ArgumentTypeInfo<?, ?>> LIMITATION_BOOL_ARGUMENT_TYPE = register("limitation_bool", () -> registerByClass(LimitationItemArgumentType.Booleans.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::booleans)));
    private static final Holder<ArgumentTypeInfo<?, ?>> LIMITATION_INT_ARGUMENT_TYPE = register("limitation_int", () -> registerByClass(LimitationItemArgumentType.Integers.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::integers)));
    private static final Holder<ArgumentTypeInfo<?, ?>> LIMITATION_REAL_ARGUMENT_TYPE = register("limitation_reals", () -> registerByClass(LimitationItemArgumentType.Doubles.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::doubles)));
    private static final Holder<ArgumentTypeInfo<?, ?>> LIMITATION_ID_ARGUMENT_TYPE = register("limitation_id", () -> registerByClass(LimitationIDArgumentType.class, SingletonArgumentInfo.contextFree(LimitationIDArgumentType::new)));
    private static final Holder<ArgumentTypeInfo<?, ?>> STAMINA_TYPE_ARGUMENT_TYPE = register("stamina_type", () -> registerByClass(StaminaTypeArgumentType.class, SingletonArgumentInfo.contextFree(StaminaTypeArgumentType::new)));

    private static synchronized <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> I registerByClass(Class<A> infoClass, I argumentTypeInfo) {
        ArgumentTypeInfosAccessor.getByClass().put(infoClass, argumentTypeInfo);
        return argumentTypeInfo;
    }

    private static Holder<ArgumentTypeInfo<?, ?>> register(String path, Supplier<ArgumentTypeInfo<?, ?>> argumentTypeInfoSupplier) {
        return Registry.registerForHolder(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, ParCool.id(path), argumentTypeInfoSupplier.get());
    }

    public static void registerAll() {
    }
}
