package com.alrex.parcool.common.attachment;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class Attachments {
    public static final AttachmentType<ReadonlyStamina> STAMINA = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "stamina"),
            builder ->
                builder
                    .initializer(ReadonlyStamina::createDefault)
                    .persistent(ReadonlyStamina.CODEC)
    );
    public static final AttachmentType<Parkourability> PARKOURABILITY = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "parkourability"),
            builder -> builder.initializer(Parkourability::new)
    );

    public static void registerAll() {
    }
}
