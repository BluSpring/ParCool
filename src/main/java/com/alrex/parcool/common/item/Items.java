package com.alrex.parcool.common.item;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.block.Blocks;
import com.alrex.parcool.common.item.zipline.ZiplineRopeItem;
import io.github.fabricators_of_create.porting_lib.registry.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class Items {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ParCool.MOD_ID);
    public static final DeferredHolder<Item, Item> PARCOOL_GUIDE = ITEMS.register("parcool_guide", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> WOODEN_ZIPLINE_HOOK = ITEMS.register("wooden_zipline_hook", () -> new BlockItem(Blocks.WOODEN_ZIPLINE_HOOK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> IRON_ZIPLINE_HOOK = ITEMS.register("iron_zipline_hook", () -> new BlockItem(Blocks.IRON_ZIPLINE_HOOK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> ZIPLINE_ROPE = ITEMS.register("zipline_rope", () -> new ZiplineRopeItem(new Item.Properties()));

	public static void registerAll() {
		ITEMS.register();
	}

    @Environment(EnvType.CLIENT)
    public static void registerColors() {
        ColorProviderRegistry.ITEM.register(new ZiplineRopeItem.RopeColor(), ZIPLINE_ROPE::get);
    }
}