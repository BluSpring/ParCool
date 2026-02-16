package com.alrex.parcool.common.attachment.client;

import com.alrex.parcool.api.Effects;
import com.alrex.parcool.common.attachment.Attachments;
import com.alrex.parcool.common.attachment.ClientAttachments;
import com.alrex.parcool.common.stamina.IParCoolStaminaHandler;
import com.alrex.parcool.common.stamina.StaminaType;
import com.alrex.parcool.common.stamina.handlers.InfiniteStaminaHandler;
import net.minecraft.client.player.LocalPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class LocalStamina {
    @Nullable
    private StaminaType currentType = null;
    @Nullable
    private IParCoolStaminaHandler handler = null;

    public static LocalStamina get(LocalPlayer player) {
        return player.getAttachedOrCreate(ClientAttachments.LOCAL_STAMINA.get());
    }

    public boolean isAvailable() {
        return handler != null && currentType != null;
    }

    public boolean isInfinite(LocalPlayer player) {
        return player.isCreative() || player.isSpectator() || handler instanceof InfiniteStaminaHandler;
    }

    public void changeType(LocalPlayer player, StaminaType type) {
        currentType = type;
        handler = type.newHandler(player);
        player.setAttached(Attachments.STAMINA.get(), handler.initializeStamina(player, player.getAttachedOrCreate(Attachments.STAMINA.get())));
    }

    @Nullable
    public IParCoolStaminaHandler getHandler() {
        return handler;
    }

    public boolean isExhausted(LocalPlayer player) {
        return player.getAttachedOrCreate(Attachments.STAMINA.get()).isExhausted();
    }

    public int getValue(LocalPlayer player) {
        return player.getAttachedOrCreate(Attachments.STAMINA.get()).value();
    }

    public int getMax(LocalPlayer player) {
        return player.getAttachedOrCreate(Attachments.STAMINA.get()).max();
    }

    public void consume(LocalPlayer player, int value) {
        if (player.isCreative() || player.isSpectator()) return;
        if (handler == null) return;
        if (isInfinite(player)) return;
        if (player.hasEffect(Effects.INEXHAUSTIBLE)) return;
        player.setAttached(
                Attachments.STAMINA.get(),
                handler.consume(player, player.getAttachedOrCreate(Attachments.STAMINA.get()), value)
        );
    }

    public void recover(LocalPlayer player, int value) {
        if (player.isCreative() || player.isSpectator()) return;
        if (handler == null) return;
        player.setAttached(
                Attachments.STAMINA.get(),
                handler.recover(player, player.getAttachedOrCreate(Attachments.STAMINA.get()), value)
        );
    }

    public void onTick(LocalPlayer player) {
        if (handler == null) return;
        player.setAttached(
                Attachments.STAMINA.get(),
                handler.onTick(player, player.getAttachedOrCreate(Attachments.STAMINA.get()))
        );
    }

    public boolean shouldShowHUD(LocalPlayer player) {
        if (handler == null) return false;
        return handler.shouldShowHUD(player);
    }

    public boolean imposeExhaustionPenalty(LocalPlayer player) {
        if (handler == null) return false;
        var current = player.getAttachedOrCreate(Attachments.STAMINA.get());
        return current.isExhausted() && handler.shouldImposeExhaustionPenalty(player, current);
    }

    public void sync(LocalPlayer player) {
        player.getAttachedOrCreate(Attachments.STAMINA.get()).sync(player);
    }

    public boolean isUsingExternalStamina() {
        return handler != null && handler.isExternalStamina();
    }
}
