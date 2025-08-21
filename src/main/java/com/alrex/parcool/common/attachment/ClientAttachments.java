package com.alrex.parcool.common.attachment;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.client.Animation;
import com.alrex.parcool.common.attachment.client.LocalStamina;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientAttachments {
    public static final AttachmentType<LocalStamina> LOCAL_STAMINA = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID + ".client", "local_stamina"),
            builder -> builder.initializer(LocalStamina::new)
    );
    public static final AttachmentType<Animation> ANIMATION = AttachmentRegistry.create(
        ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID + ".client", "animation"),
            builder -> builder.initializer(Animation::new)
    );

    public static void registerAll() {
    }
}
