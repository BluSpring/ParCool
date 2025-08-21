package com.alrex.parcool.server.command.args;

import com.alrex.parcool.ParCool;
import com.mojang.brigadier.arguments.ArgumentType;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ParCoolArgumentTypeInfos {
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, ParCool.MOD_ID);
    private static final ArgumentTypeInfo<?, ?> ACTION_ARGUMENT_TYPE = register("action", ActionArgumentType.class, SingletonArgumentInfo.contextFree(ActionArgumentType::action));
    private static final ArgumentTypeInfo<?, ?> LIMITATION_BOOL_ARGUMENT_TYPE = register("limitation_bool", LimitationItemArgumentType.Booleans.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::booleans));
    private static final ArgumentTypeInfo<?, ?> LIMITATION_INT_ARGUMENT_TYPE = register("limitation_int", LimitationItemArgumentType.Integers.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::integers));
    private static final ArgumentTypeInfo<?, ?> LIMITATION_REAL_ARGUMENT_TYPE = register("limitation_reals", LimitationItemArgumentType.Doubles.class, SingletonArgumentInfo.contextFree(LimitationItemArgumentType::doubles));
    private static final ArgumentTypeInfo<?, ?> LIMITATION_ID_ARGUMENT_TYPE = register("limitation_id", LimitationIDArgumentType.class, SingletonArgumentInfo.contextFree(LimitationIDArgumentType::new));
    private static final ArgumentTypeInfo<?, ?> STAMINA_TYPE_ARGUMENT_TYPE = register("stamina_type", StaminaTypeArgumentType.class, SingletonArgumentInfo.contextFree(StaminaTypeArgumentType::new));

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> ArgumentTypeInfo<A, T> register(String id, Class<? extends A> clazz, ArgumentTypeInfo<A, T> serializer) {
        ArgumentTypeRegistry.registerArgumentType(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, id), clazz, serializer);
        return serializer;
    }
    
    public static void registerAll() {
    }
}
