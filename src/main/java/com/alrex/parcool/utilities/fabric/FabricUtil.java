package com.alrex.parcool.utilities.fabric;

import io.github.fabricators_of_create.porting_lib.blocks.extensions.CustomFrictionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FabricUtil {
    public static float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (state.getBlock() instanceof CustomFrictionBlock frictionBlock)
            return frictionBlock.getFriction(state, level, pos, entity);

        return state.getBlock().getFriction();
    }
}
