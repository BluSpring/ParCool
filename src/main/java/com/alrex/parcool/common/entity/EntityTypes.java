package com.alrex.parcool.common.entity;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.entity.zipline.ZiplineRopeEntity;
import com.alrex.parcool.common.zipline.Zipline;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class EntityTypes {
    public static final Supplier<EntityType<ZiplineRopeEntity>> ZIPLINE_ROPE = register(
            "zipline_rope",
            () -> EntityType.Builder
                    .of((EntityType.EntityFactory<ZiplineRopeEntity>) ZiplineRopeEntity::new, MobCategory.MISC)
                    .noSave()
                    .clientTrackingRange((int) (Zipline.MAXIMUM_HORIZONTAL_DISTANCE / 1.9))
                    .updateInterval(Integer.MAX_VALUE)
                    .noSummon()
                    .sized(0.1f, 0.1f)
                    .fireImmune()
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "zipline_rope")
                    ))
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String path, Supplier<EntityType<T>> entityTypeSupplier) {
        var type = Registry.register(BuiltInRegistries.ENTITY_TYPE, ParCool.id(path), entityTypeSupplier.get());
        return () -> type;
    }

    public static void registerAll() {
    }
}
