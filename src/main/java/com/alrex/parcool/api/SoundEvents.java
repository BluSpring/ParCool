package com.alrex.parcool.api;

import com.alrex.parcool.ParCool;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SoundEvents {
    private static final SoundEvent VAULT_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.vault"));
    private static final SoundEvent VERTICAL_WALL_RUN_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.v_wall_run"));
    private static final SoundEvent HORIZONTAL_WALL_RUN_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.h_wall_run"));
    private static final SoundEvent BREAKFALL_JUST_TIME_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.breakfall.just"));
    private static final SoundEvent ROLL_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.breakfall.roll"));
    private static final SoundEvent SAFETY_TAP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.breakfall.tap"));
    private static final SoundEvent CATLEAP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.catleap"));
    private static final SoundEvent CHARGE_JUMP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.charge_jump"));
    private static final SoundEvent WALL_JUMP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.wall_jump"));
    private static final SoundEvent CLING_TO_CLIFF_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.cling_to_cliff.grab"));
    private static final SoundEvent CLING_TO_CLIFF_JUMP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.cling_to_cliff.jump"));
    private static final SoundEvent HANG_DOWN_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.hang_down.grab"));
    private static final SoundEvent HANG_DOWN_JUMP_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.hang_down.jump"));
    private static final SoundEvent SLIDE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.slide"));
    private static final SoundEvent DODGE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "action.dodge"));
    private static final SoundEvent PARCOOL_ENABLE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "parcool.enable"));
    private static final SoundEvent PARCOOL_DISABLE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "parcool.disable"));
    private static final SoundEvent ZIPLINE_SET_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "zipline.set"));
    private static final SoundEvent ZIPLINE_REMOVE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "zipline.remove"));

    public static final Supplier<SoundEvent> VAULT = register("action.vault", () -> VAULT_SOUND);
    public static final Supplier<SoundEvent> VERTICAL_WALL_RUN = register("action.v_wall_run", () -> VERTICAL_WALL_RUN_SOUND);
    public static final Supplier<SoundEvent> HORIZONTAL_WALL_RUN = register("action.h_wall_run", () -> HORIZONTAL_WALL_RUN_SOUND);
    public static final Supplier<SoundEvent> BREAKFALL_JUST_TIME = register("action.breakfall.just", () -> BREAKFALL_JUST_TIME_SOUND);
    public static final Supplier<SoundEvent> ROLL = register("action.breakfall.roll", () -> ROLL_SOUND);
    public static final Supplier<SoundEvent> SAFETY_TAP = register("action.breakfall.tap", () -> SAFETY_TAP_SOUND);
    public static final Supplier<SoundEvent> CATLEAP = register("action.catleap", () -> CATLEAP_SOUND);
    public static final Supplier<SoundEvent> CHARGE_JUMP = register("action.charge_jump", () -> CHARGE_JUMP_SOUND);
    public static final Supplier<SoundEvent> WALL_JUMP = register("action.wall_jump", () -> WALL_JUMP_SOUND);
    public static final Supplier<SoundEvent> CLING_TO_CLIFF = register("action.cling_to_cliff.grab", () -> CLING_TO_CLIFF_SOUND);
    public static final Supplier<SoundEvent> CLING_TO_CLIFF_JUMP = register("action.cling_to_cliff.jump", () -> CLING_TO_CLIFF_JUMP_SOUND);
    public static final Supplier<SoundEvent> HANG_DOWN = register("action.hang_down.grab", () -> HANG_DOWN_SOUND);
    public static final Supplier<SoundEvent> HANG_DOWN_JUMP = register("hang_down.jump", () -> HANG_DOWN_JUMP_SOUND);
    public static final Supplier<SoundEvent> SLIDE = register("action.slide", () -> SLIDE_SOUND);
    public static final Supplier<SoundEvent> DODGE = register("action.dodge", () -> DODGE_SOUND);
    public static final Supplier<SoundEvent> PARCOOL_ENABLE = register("action.enable", () -> PARCOOL_ENABLE_SOUND);
    public static final Supplier<SoundEvent> PARCOOL_DISABLE = register("action.disable", () -> PARCOOL_DISABLE_SOUND);
    public static final Supplier<SoundEvent> ZIPLINE_SET = register("zipline.set", () -> ZIPLINE_SET_SOUND);
    public static final Supplier<SoundEvent> ZIPLINE_REMOVE = register("zipline.remove", () -> ZIPLINE_REMOVE_SOUND);

    private static Supplier<SoundEvent> register(String name, Supplier<SoundEvent> soundSupplier) {
        var soundEvent = Registry.register(BuiltInRegistries.SOUND_EVENT, ParCool.id(name), soundSupplier.get());
        return () -> soundEvent;
    }

    public static void registerAll() {
    }
}
