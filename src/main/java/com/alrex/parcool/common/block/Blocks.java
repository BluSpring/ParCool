package com.alrex.parcool.common.block;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.block.zipline.IronZiplineHookBlock;
import com.alrex.parcool.common.block.zipline.WoodenZiplineHookBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;

public class Blocks {
    public static final Supplier<Block> WOODEN_ZIPLINE_HOOK = register(
            "wooden_zipline_hook",
            (name) -> new WoodenZiplineHookBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.WOOD)
                    .strength(1.0f, 3.0f)
                    .sound(SoundType.WOOD)
                    .setId(ResourceKey.create(Registries.BLOCK, name))
            )
    );
    public static final Supplier<Block> IRON_ZIPLINE_HOOK = register(
            "iron_zipline_hook",
            (name) -> new IronZiplineHookBlock(BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.METAL)
                    .strength(1.0f, 3.0f)
                    .noCollission()
                    .sound(SoundType.CHAIN)
                    .setId(ResourceKey.create(Registries.BLOCK, name))
            )
    );

    private static <T extends Block> Supplier<T> register(String path, Function<ResourceLocation, T> blockSupplier) {
        var id = ParCool.id(path);
        var block = Registry.register(BuiltInRegistries.BLOCK, id, blockSupplier.apply(id));
        return () -> block;
    }

    public static void registerAll() {
    }

}
