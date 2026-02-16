package com.alrex.parcool.common.handlers;

import com.alrex.parcool.api.unstable.action.ParCoolActionEvent;
import com.alrex.parcool.common.action.impl.*;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.common.network.payload.StartBreakfallEventPayload;
import com.alrex.parcool.config.ParCoolConfig;
import com.alrex.parcool.utilities.WorldUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerDamageHandler {
	public static void init() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> onAttack(entity, source));
	}

    public static boolean onAttack(LivingEntity entity, DamageSource source) {
        if (entity instanceof Player player) {
            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return true;
            Dodge dodge = parkourability.get(Dodge.class);
            if (dodge.isDoing()) {
                if (!parkourability.getServerLimitation().get(ParCoolConfig.Server.Booleans.DodgeProvideInvulnerableFrame))
                    return true;
                if (source.is(DamageTypeTags.BYPASSES_ARMOR)) return true;
                if (dodge.getDoingTick() <= 10) {
                    return false;
                }
            }
        }

		return true;
    }

    public static float onFall(LivingEntity entity, double distance, float damageMultiplier) {
        if (entity instanceof ServerPlayer player) {

            Parkourability parkourability = Parkourability.get(player);

			if (parkourability.get(BreakfallReady.class).isDoing()
					&& (parkourability.getActionInfo().can(Tap.class)
					|| parkourability.getActionInfo().can(Roll.class))
			) {
				boolean justTime = parkourability.get(BreakfallReady.class).getDoingTick() < parkourability.getLimitedValue(
						ParCoolConfig.Client.Integers.JustTimeBreakfallTick,
						ParCoolConfig.Server.Integers.MaxJustTimeBreakfallTick
				);

				if (distance > parkourability.getLimitedValue(
						ParCoolConfig.Client.Doubles.LowestFallDistanceForBreakfall,
						ParCoolConfig.Server.Doubles.MinLowestFallDistanceForBreakfall
				)) {
                    ServerPlayNetworking.send(player, new StartBreakfallEventPayload(justTime));
				} else {
					return damageMultiplier;
				}
				double damageRemoveHeight = parkourability.getLimitedValue(
						ParCoolConfig.Client.Doubles.DamageCompleteRemovableHeightBreakfall,
						ParCoolConfig.Server.Doubles.MaxDamageCompleteRemovableHeightBreakfall
				);
				if (distance < damageRemoveHeight || (justTime && distance < damageRemoveHeight * 1.34)) {
					return 0f;
				} else {
					float damageReductionRate = (float) parkourability.getLimitedValue(
							ParCoolConfig.Client.Doubles.DamageReductionRateBreakfall,
							ParCoolConfig.Server.Doubles.MaxDamageReductionRateBreakfall
					);
					return damageMultiplier * (justTime ? 0.66f * damageReductionRate : damageReductionRate);
				}
			} else {
				HideInBlock hideInBlock = parkourability.get(HideInBlock.class);
				if (hideInBlock.isStandbyInAir(parkourability)
						&& parkourability.getActionInfo().can(HideInBlock.class)
						&& !(new ParCoolActionEvent.TryToStartEvent(player, hideInBlock)).sendEvent().isCanceled()
						&& !(new ParCoolActionEvent.TryToStart(player, hideInBlock)).sendEvent().isCanceled()
				) {
					Tuple<BlockPos, BlockPos> area = WorldUtil.getHideAbleSpace(player, new BlockPos(player.blockPosition().below()));
					if (area != null) {
						boolean stand = player.getBbHeight() < (Math.abs(area.getB().getY() - area.getA().getY()) + 1);
						if (!stand) {
							if (distance < 10) {
								return 0f;
							} else {
								return damageMultiplier * 0.4f;
							}
						}
					}
				}
			}
		} else if (entity instanceof Player player) {
			if (!player.isLocalPlayer()) {
				return 0f;
			}
			Parkourability parkourability = Parkourability.get(player);
			if (parkourability.getAdditionalProperties().getNotLandingTick() > 5 && distance < 0.4f) {
				parkourability.get(ChargeJump.class).onLand(player, parkourability);
			}
		}

		return damageMultiplier;
	}
}
