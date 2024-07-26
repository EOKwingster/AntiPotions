package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID)
public class MobEffectEventHandler {

    @SubscribeEvent
    private static void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();
        Level level = entity.level();

        //relish mob effect: multiply food values
        if (!level.isClientSide()) {
            MobEffectInstance relishEffectInstance = entity.getEffect(APMobEffects.RELISH);
            if (relishEffectInstance != null) {
                FoodProperties foodProperties = itemStack.getFoodProperties(entity);
                if (foodProperties != null) {
                    int potionLevel = relishEffectInstance.getAmplifier() + 1;
                    int nutrition = foodProperties.nutrition();
                    float saturation = foodProperties.saturation();
                    if (entity instanceof Player player) {
                        player.getFoodData().eat(nutrition * potionLevel, saturation * potionLevel);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    private static void livingEntityUseItemStart(LivingEntityUseItemEvent.Start event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();

        //relish mob effect: fasten consuming foods
        MobEffectInstance relishEffectInstance = entity.getEffect(APMobEffects.RELISH);
        if (relishEffectInstance != null) {
            if (itemStack.is(Tags.Items.FOODS)) {
                int amplifier = relishEffectInstance.getAmplifier();
                int duration = event.getDuration();
                int newDuration = (int) (duration * Math.pow(0.65, amplifier+1));
                if (newDuration < 1) {
                    newDuration = 1;
                }
                event.setDuration(newDuration);
            }
        }
    }

    @SubscribeEvent
    private static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        MobEffectInstance effectInstance = event.getEffectInstance();

        //relish mob effect: prevent confusion
        if (effectInstance != null && effectInstance.is(MobEffects.CONFUSION)) {
            if (event.getEntity().hasEffect(APMobEffects.RELISH)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

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
