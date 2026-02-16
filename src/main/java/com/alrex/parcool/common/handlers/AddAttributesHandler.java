package com.alrex.parcool.common.handlers;


import com.alrex.parcool.api.Attributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class AddAttributesHandler {
    private static AttributeSupplier playerAttributeSupplier;

    public static AttributeSupplier getPlayerAttributeSupplier(AttributeSupplier original) {
        if (playerAttributeSupplier == null) {
            var builder = new AttributeSupplier.Builder();

            // Copy attributes
            for (Attribute attribute : BuiltInRegistries.ATTRIBUTE) {
                Holder<Attribute> attributeHolder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute);
                if (original.hasAttribute(attributeHolder)) {
                    builder.add(attributeHolder, original.getValue(attributeHolder));
                }
            }

            // Add custom attributes
            builder.add(Attributes.MAX_STAMINA);
            builder.add(Attributes.STAMINA_RECOVERY);

            playerAttributeSupplier = builder.build();
        }

        return playerAttributeSupplier;
    }
}
