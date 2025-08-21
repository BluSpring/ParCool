package com.alrex.parcool.common.handlers;

import com.alrex.parcool.api.unstable.action.ParCoolActionEvent;
import com.alrex.parcool.common.action.impl.*;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.network.payload.StartBreakfallEventPayload;
import com.alrex.parcool.config.ParCoolConfig;
import com.alrex.parcool.utilities.WorldUtil;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingFallEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerDamageHandler {
    public static void init() {
        LivingHurtEvent.EVENT.register(PlayerDamageHandler::onAttack);
        LivingFallEvent.EVENT.register(PlayerDamageHandler::onFall);
    }

    public static void onAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;
            Dodge dodge = parkourability.get(Dodge.class);
            if (dodge.isDoing()) {
                if (!parkourability.getServerLimitation().get(ParCoolConfig.Server.Booleans.DodgeProvideInvulnerableFrame))
                    return;
                if (event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) return;
                if (dodge.getDoingTick() <= 10) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {

            Parkourability parkourability = Parkourability.get(player);

			if (parkourability.get(BreakfallReady.class).isDoing()
					&& (parkourability.getActionInfo().can(Tap.class)
					|| parkourability.getActionInfo().can(Roll.class))
			) {
				boolean justTime = parkourability.get(BreakfallReady.class).getDoingTick() < 5;
				float distance = event.getDistance();
				if (distance > parkourability.getClientInfo().get(ParCoolConfig.Client.Doubles.LowestFallDistanceForBreakfall)) {
                    ServerPlayNetworking.send(player, new StartBreakfallEventPayload(justTime));
				} else {
					return;
				}
				if (distance < 6 || (justTime && distance < 8)) {
					event.setCanceled(true);
				} else {
					event.setDamageMultiplier(event.getDamageMultiplier() * (justTime ? 0.4f : 0.6f));
				}
			} else {
				HideInBlock hideInBlock = parkourability.get(HideInBlock.class);
				if (hideInBlock.isStandbyInAir(parkourability)
						&& parkourability.getActionInfo().can(HideInBlock.class)
                        && !(new ParCoolActionEvent.TryToStartEvent(player, hideInBlock)).post()
				) {
					Tuple<BlockPos, BlockPos> area = WorldUtil.getHideAbleSpace(player, new BlockPos(player.blockPosition().below()));
					if (area != null) {
						boolean stand = player.getBbHeight() < (Math.abs(area.getB().getY() - area.getA().getY()) + 1);
						if (!stand) {
							if (event.getDistance() < 10) {
								event.setCanceled(true);
							} else {
								event.setDamageMultiplier(event.getDamageMultiplier() * 0.4f);
							}
						}
					}
				}
			}
		} else if (event.getEntity() instanceof Player player) {
			if (!player.isLocalPlayer()) {
				return;
			}
			Parkourability parkourability = Parkourability.get(player);
			if (parkourability.getAdditionalProperties().getNotLandingTick() > 5 && event.getDistance() < 0.4f) {
				parkourability.get(ChargeJump.class).onLand(player, parkourability);
			}
		}
	}
}
