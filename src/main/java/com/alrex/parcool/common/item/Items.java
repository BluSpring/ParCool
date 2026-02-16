package com.alrex.parcool.common.item;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.block.Blocks;
import com.alrex.parcool.common.item.zipline.ZiplineRopeItem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public class Items {
    public static final Supplier<Item> PARCOOL_GUIDE = register("parcool_guide", (name) -> new Item(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, name))));
    public static final Supplier<Item> WOODEN_ZIPLINE_HOOK = register("wooden_zipline_hook", (name) -> new BlockItem(Blocks.WOODEN_ZIPLINE_HOOK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, name))));
    public static final Supplier<Item> IRON_ZIPLINE_HOOK = register("iron_zipline_hook", (name) -> new BlockItem(Blocks.IRON_ZIPLINE_HOOK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, name))));
    public static final Supplier<Item> ZIPLINE_ROPE = register("zipline_rope", (name) -> new ZiplineRopeItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, name))));

    private static Supplier<Item> register(String name, Function<ResourceLocation, Item> itemFunction) {
        var id = ParCool.id(name);
        var item = Registry.register(BuiltInRegistries.ITEM, id, itemFunction.apply(id));
        return () -> item;
    }

	public static void registerAll() {
	}
}