package com.alrex.parcool.client.hud.impl;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.api.client.gui.ParCoolHUDEvent;
import com.alrex.parcool.common.attachment.Attachments;
import com.alrex.parcool.common.attachment.client.LocalStamina;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.config.ParCoolConfig;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StaminaHUDController implements HudElement {
	public static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "hud.stamina");
	LightStaminaHUD lightStaminaHUD;
	StaminaHUD staminaHUD;

	public StaminaHUDController() {
		lightStaminaHUD = new LightStaminaHUD();
		staminaHUD = new StaminaHUD();
	}

	public void onTick(Minecraft client) {
		LocalPlayer player = client.player;
		if (player == null || player.isCreative()) return;
		lightStaminaHUD.onTick(client, player);
		staminaHUD.onTick(client, player);
	}

	@Override
	public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker partialTick) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		if (!ParCoolConfig.Client.Booleans.ParCoolIsActive.get()) return;

		Parkourability parkourability = Parkourability.get(player);

		var localStamina = LocalStamina.get(player);
		var stamina = player.getAttachedOrCreate(Attachments.STAMINA.get());

		if (ParCoolConfig.Client.Booleans.HideStaminaHUDWhenStaminaIsInfinite.get() &&
				parkourability.getActionInfo().isStaminaInfinite(localStamina, player)
		) return;

		if (!localStamina.shouldShowHUD(player)) return;

		var event = new ParCoolHUDEvent.RenderEvent(graphics, partialTick);
		ParCoolHUDEvent.RenderEvent.EVENT.invoker().onRender(event);
		if (event.isCanceled())
			return;

		switch (ParCoolConfig.Client.getInstance().StaminaHUDType.get()) {
			case Light:
				lightStaminaHUD.render(graphics, parkourability, stamina, partialTick.getGameTimeDeltaPartialTick(true));
				break;
			case Normal:
				staminaHUD.render(graphics, parkourability, stamina, partialTick.getGameTimeDeltaPartialTick(true));
				break;
		}
	}
}
