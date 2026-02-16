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
import com.alrex.parcool.common.network.NetworkRegistries;
import com.alrex.parcool.common.potion.Potions;
import com.alrex.parcool.common.registries.EventBusForgeRegistry;
import com.alrex.parcool.config.ParCoolConfig;
import com.alrex.parcool.extern.AdditionalMods;
import com.alrex.parcool.server.command.CommandRegistry;
import com.alrex.parcool.server.command.args.ParCoolArgumentTypeInfos;
import com.alrex.parcool.server.limitation.Limitations;
import com.mojang.brigadier.CommandDispatcher;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParCool implements ModInitializer {
	public static final String MOD_ID = "parcool";

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(ParCool.class);

	@Override
	public void onInitialize() {
		EventBusForgeRegistry.register();
		this.setup();
//		eventBus.register(AddAttributesHandler.class); // DefaultAttributesMixin
		NetworkRegistries.onRegisterPayload();
//		eventBus.register(HUDRegistry.class); // ParCoolClient

		Effects.registerAll();
		Potions.registerAll();
		Attributes.registerAll();
		SoundEvents.registerAll();
		Blocks.registerAll();
		Items.registerAll();
		CreativeTabs.registerAll();
		EntityTypes.registerAll();
		TileEntities.registerAll();
		DataComponents.registerAll();
		Attachments.registerAll();
		ParCoolArgumentTypeInfos.registerAll();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> registerCommand(dispatcher));

		ServerLifecycleEvents.SERVER_STARTING.register(Limitations::init);
		ServerLifecycleEvents.SERVER_STOPPING.register(Limitations::save);

		ConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.SERVER, ParCoolConfig.Server.getConfigSpec());
		ConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ParCoolConfig.Client.getConfigSpec());

		this.loaded();
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
