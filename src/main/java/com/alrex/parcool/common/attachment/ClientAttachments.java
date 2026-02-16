package com.alrex.parcool.common.attachment;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.client.Animation;
import com.alrex.parcool.common.attachment.client.LocalStamina;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientAttachments {
    public static final Supplier<AttachmentType<LocalStamina>> LOCAL_STAMINA = register(AttachmentRegistry.create(
            ParCool.id("local_stamina"),
            (builder) -> builder.initializer(LocalStamina::new)
    ));
    public static final Supplier<AttachmentType<Animation>> ANIMATION = register(AttachmentRegistry.create(
            ParCool.id("animation"),
            (builder) -> builder.initializer(Animation::new)
    ));

    private static <A> Supplier<AttachmentType<A>> register(AttachmentType<A> type) {
        return () -> type;
    }

    public static void registerAll() {
    }
}
