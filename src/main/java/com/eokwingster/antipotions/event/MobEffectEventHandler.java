package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID)
public class MobEffectEventHandler {
    @SubscribeEvent
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        //antiWither mob effect
        MobEffectInstance witherEffectInstance = entity.getEffect(MobEffects.WITHER);
        MobEffectInstance antiWitherEffectInstance = entity.getEffect(APMobEffects.ANTI_WITHER);
        if (witherEffectInstance != null && antiWitherEffectInstance != null) {
            if (shouldApplyEffectTickThisTick(witherEffectInstance, entity)) {
                if (shouldApplyEffectTickThisTick(
                        witherEffectInstance.getEffect().value(),
                        getDuration(witherEffectInstance, entity),
                        antiWitherEffectInstance.getAmplifier()
                )) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private static boolean shouldApplyEffectTickThisTick(MobEffectInstance mobEffectInstance, LivingEntity entity) {
        int duration = getDuration(mobEffectInstance, entity);
        int amplifier = mobEffectInstance.getAmplifier();
        MobEffect mobEffect = mobEffectInstance.getEffect().value();
        return shouldApplyEffectTickThisTick(mobEffect, duration, amplifier);
    }

    private static boolean shouldApplyEffectTickThisTick(MobEffect mobEffect, int duration, int amplifier) {
        return mobEffect.shouldApplyEffectTickThisTick(duration, amplifier);
    }

    private static int getDuration(MobEffectInstance mobEffectInstance, LivingEntity entity) {
        if (mobEffectInstance.isInfiniteDuration()) {
            return entity.tickCount;
        } else {
            return mobEffectInstance.getDuration();
        }
    }
}
