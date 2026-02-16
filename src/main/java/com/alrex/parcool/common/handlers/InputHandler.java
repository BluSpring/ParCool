package com.alrex.parcool.common.handlers;

import com.alrex.parcool.client.input.KeyBindings;
import com.alrex.parcool.common.action.impl.ClingToCliff;
import com.alrex.parcool.common.action.impl.HideInBlock;
import com.alrex.parcool.common.action.impl.RideZipline;
import com.alrex.parcool.common.action.impl.WallSlide;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.mixin.client.keybinding.KeyBindingAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionResult;

public class InputHandler {
    public static void init() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (player instanceof LocalPlayer) {
                InputConstants.Key useKey = ((KeyBindingAccessor) Minecraft.getInstance().options.keyUse).fabric_getBoundKey();

                Parkourability parkourability = Parkourability.get(player);
                if (parkourability == null) return InteractionResult.PASS;
                if (parkourability.get(HideInBlock.class).isDoing()) {
                    return InteractionResult.FAIL;
                }
                if (parkourability.get(ClingToCliff.class).isDoing()) {
                    if (useKey.equals(((KeyBindingAccessor) KeyBindings.getKeyGrabWall()).fabric_getBoundKey())) {
                        return InteractionResult.FAIL;
                    }
                }
                if (parkourability.get(RideZipline.class).isDoing()) {
                    if (useKey.equals(((KeyBindingAccessor) KeyBindings.getKeyRideZipline()).fabric_getBoundKey())) {
                        return InteractionResult.FAIL;
                    }
                }
                if (parkourability.get(WallSlide.class).isDoing()) {
                    if (useKey.equals(((KeyBindingAccessor) KeyBindings.getKeyWallSlide()).fabric_getBoundKey())) {
                        return InteractionResult.FAIL;
                    }
                }
            }

            return InteractionResult.PASS;
        });
    }
}
