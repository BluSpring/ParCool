package com.alrex.parcool.common.item;

import com.alrex.parcool.ParCool;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

public class CreativeTabs {
    public static final Holder<CreativeModeTab> ITEMS = register("items", () -> FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.PARCOOL_GUIDE.get()))
            .title(Component.translatable("itemGroup.ParCool"))
            .hideTitle()
            .displayItems((params, output) -> {
                output.accept(Items.IRON_ZIPLINE_HOOK.get());
                output.accept(Items.WOODEN_ZIPLINE_HOOK.get());
                output.accept(Items.ZIPLINE_ROPE.get());
                Arrays.stream(DyeColor.values())
                        .map(DyeItem::byColor)
                        .map(dye -> {
                            var coloredRope = new ItemStack(Items.ZIPLINE_ROPE.get());
                            return DyedItemColor.applyDyes(coloredRope, Collections.singletonList(dye));
                        })
                        .forEach(output::accept);
            })
            .build()
    );

    private static Holder<CreativeModeTab> register(String name, Supplier<CreativeModeTab> tabSupplier) {
        return Registry.registerForHolder(BuiltInRegistries.CREATIVE_MODE_TAB, ParCool.id(name), tabSupplier.get());
    }

    public static void registerAll() {
    }
}
