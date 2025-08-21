package com.alrex.parcool.extern.paraglider;

import com.alrex.parcool.api.unstable.animation.ParCoolAnimationInfoEvent;
import com.alrex.parcool.extern.AdditionalMods;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EventConsumerForParaglider {
    public static void init() {
        ParCoolAnimationInfoEvent.EVENT.register(EventConsumerForParaglider::onUpdateAnimateInfo);
    }

    @Environment(EnvType.CLIENT)
    public static void onUpdateAnimateInfo(ParCoolAnimationInfoEvent event) {
        if (AdditionalMods.paraglider().isFallingWithParaglider(event.getPlayer())) {
            event.getOption().cancelAnimation();
        }
    }
}