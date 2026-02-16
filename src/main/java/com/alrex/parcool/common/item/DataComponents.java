package com.alrex.parcool.common.item;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.item.component.ZiplinePositionComponent;
import com.alrex.parcool.common.item.component.ZiplineTensionComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DataComponents {
    public static final Supplier<DataComponentType<ZiplinePositionComponent>> ZIPLINE_POSITION = registerComponentType(
            "zipline_pos",
            builder -> builder.persistent(ZiplinePositionComponent.CODEC).networkSynchronized(ZiplinePositionComponent.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<ZiplineTensionComponent>> ZIPLINE_TENSION = registerComponentType(
            "zipline_tension",
            builder -> builder.persistent(ZiplineTensionComponent.CODEC).networkSynchronized(ZiplineTensionComponent.STREAM_CODEC)
    );

    private static <T> Supplier<DataComponentType<T>> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builderConsumer) {
        var componentType = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ParCool.id(name), builderConsumer.apply(DataComponentType.builder()).build());
        return () -> componentType;
    }

    public static void registerAll() {
    }
}
