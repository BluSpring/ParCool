package com.alrex.parcool;

import com.alrex.parcool.api.Attributes;
import com.alrex.parcool.api.Effects;
import com.alrex.parcool.api.SoundEvents;
import com.alrex.parcool.client.hud.HUDRegistry;
import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.client.renderer.Renderers;
import com.alrex.parcool.common.attachment.Attachments;
import com.alrex.parcool.common.attachment.ClientAttachments;
import com.alrex.parcool.common.block.Blocks;
import com.alrex.parcool.common.block.TileEntities;
import com.alrex.parcool.common.entity.EntityTypes;
import com.alrex.parcool.common.handlers.AddAttributesHandler;
import com.alrex.parcool.common.item.CreativeTabs;
import com.alrex.parcool.common.item.DataComponents;
import com.alrex.parcool.common.item.Items;
import com.alrex.parcool.common.item.recipe.Recipes;
import com.alrex.parcool.common.network.NetworkRegistries;
import com.alrex.parcool.common.potion.Potions;
import com.alrex.parcool.common.registries.EventBusForgeRegistry;
import com.alrex.parcool.config.ParCoolConfig;
import com.alrex.parcool.extern.AdditionalMods;
import com.alrex.parcool.server.command.CommandRegistry;
import com.alrex.parcool.server.command.args.ParCoolArgumentTypeInfos;
import com.alrex.parcool.server.limitation.Limitations;
import com.mojang.brigadier.CommandDispatcher;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParCool implements ModInitializer {
	public static final String MOD_ID = "parcool";

	public static final Logger LOGGER = LogManager.getLogger();

	public void onInitialize() {
        Effects.registerAll();
        Potions.registerAll();
        Attributes.registerAll();
        SoundEvents.registerAll();
        Blocks.registerAll();
        Items.registerAll();
        CreativeTabs.registerAll();
        Recipes.registerAll();
        EntityTypes.registerAll();
        TileEntities.registerAll();
        DataComponents.registerAll();
        Attachments.registerAll();
        ParCoolArgumentTypeInfos.registerAll();

		EventBusForgeRegistry.register();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            EventBusForgeRegistry.registerClient();
            KeyBindings.register();
            Renderers.register();
            Items.registerColors();
			ClientAttachments.registerAll();
        }
        this.setup();
        this.loaded();
        AddAttributesHandler.init();
        NetworkRegistries.init();
        HUDRegistry.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> registerCommand(dispatcher));
        ServerLifecycleEvents.SERVER_STARTING.register(server -> Limitations.init(server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> Limitations.save(server));

		ConfigRegistry.registerConfig(MOD_ID, ModConfig.Type.SERVER, ParCoolConfig.Server.getConfigSpec());
		ConfigRegistry.registerConfig(MOD_ID, ModConfig.Type.CLIENT, ParCoolConfig.Client.getConfigSpec());
	}

	private void loaded() {
		AdditionalMods.init();
		switch (FabricLoader.getInstance().getEnvironmentType()) {
			case CLIENT -> AdditionalMods.initInClient();
            case SERVER -> AdditionalMods.initInDedicatedServer();
		}
	}

	private void setup() {
	}

	private void registerCommand(final CommandDispatcher<CommandSourceStack> dispatcher) {
		CommandRegistry.register(dispatcher);
	}
}
