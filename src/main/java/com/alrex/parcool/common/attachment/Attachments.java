package com.alrex.parcool.common.attachment;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import java.util.function.Supplier;

public class Attachments {
    public static final Supplier<AttachmentType<ReadonlyStamina>> STAMINA = register(
            AttachmentRegistry.create(ParCool.id("stamina"), builder -> builder
                    .initializer(ReadonlyStamina::createDefault)
                    .persistent(ReadonlyStamina.CODEC.codec())
            )
    );
    public static final Supplier<AttachmentType<Parkourability>> PARKOURABILITY = register(
            AttachmentRegistry.create(ParCool.id("parkourability"),
                builder -> builder.initializer(Parkourability::new)
            )
    );

    private static <A> Supplier<AttachmentType<A>> register(AttachmentType<A> type) {
        return () -> type;
    }

    public static void registerAll() {
    }
}
