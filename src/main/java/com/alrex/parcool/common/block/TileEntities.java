package com.alrex.parcool.common.block;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.block.zipline.ZiplineHookTileEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Supplier;

public class TileEntities {
    public static final Supplier<BlockEntityType<ZiplineHookTileEntity>> ZIPLINE_HOOK = register(
            "zipline_hook",
            () -> FabricBlockEntityTypeBuilder.create(
                    (BlockPos pos, BlockState state) -> new ZiplineHookTileEntity(TileEntities.ZIPLINE_HOOK.get(), pos, state),
                    Blocks.WOODEN_ZIPLINE_HOOK.get(), Blocks.IRON_ZIPLINE_HOOK.get()
            ).build()
    );

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String path, Supplier<BlockEntityType<T>> typeSupplier) {
        var type = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ParCool.id(path), typeSupplier.get());
        return () -> type;
    }

    public static void registerAll() {
    }
}
