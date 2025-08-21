package com.alrex.parcool.client.renderer;

import com.alrex.parcool.client.renderer.entity.ZiplineRopeRenderer;
import com.alrex.parcool.common.entity.EntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Renderers {
    public static void register() {
        EntityRendererRegistry.register(EntityTypes.ZIPLINE_ROPE.get(), ZiplineRopeRenderer::new);
    }
}
