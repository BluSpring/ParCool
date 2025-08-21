package com.alrex.parcool.common.block;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.block.zipline.ZiplineHookTileEntity;
import io.github.fabricators_of_create.porting_lib.registry.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TileEntities {
    private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ParCool.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ZiplineHookTileEntity>> ZIPLINE_HOOK = REGISTER.register(
            "zipline_hook",
            () -> BlockEntityType.Builder.of(
                    (pos, state) -> new ZiplineHookTileEntity(TileEntities.ZIPLINE_HOOK.get(), pos, state),
                    Blocks.WOODEN_ZIPLINE_HOOK.get(),
                    Blocks.IRON_ZIPLINE_HOOK.get()
            ).build(null)
    );

    public static void registerAll() {
        REGISTER.register();
    }
}
