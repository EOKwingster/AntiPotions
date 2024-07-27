package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID)
public class MobEffectEventHandler {

    @SubscribeEvent
    private static void EntityTickPost(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //visibility mob effect: prevent invisibility
            if (entity instanceof LivingEntity livingEntity) {
                MobEffectInstance visibilityInstance = livingEntity.getEffect(APMobEffects.VISIBILITY);
                if (visibilityInstance != null) {
                    livingEntity.setInvisible(false);
                }
            }
        }
    }

    @SubscribeEvent
    private static void LivingDamagePre(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //vulnerable mob effect: multiply damage by potionLevel * 0.2
            MobEffectInstance vulnerableEffectInstance = entity.getEffect(APMobEffects.VULNERABLE);
            if (vulnerableEffectInstance != null) {
                //get damage before absorption
                DamageContainer container = event.getContainer();
                float absorption = container.getReduction(DamageContainer.Reduction.ABSORPTION);
                float damageBeforeAbsorption = container.getNewDamage() + absorption;
                //get vulnerable damage before absorption
                int potionLevel5 = (vulnerableEffectInstance.getAmplifier() + 1) * 5;
                int j = 25 + potionLevel5;
                float f = damageBeforeAbsorption * (float) j;
                float newDamageBeforeAbsorption = f / 25F;
                //recompute absorption
                float oldAbsorptionAmount = entity.getAbsorptionAmount() + absorption;
                container.setReduction(DamageContainer.Reduction.ABSORPTION, Math.min(oldAbsorptionAmount, newDamageBeforeAbsorption));
                float absorbed = Math.min(newDamageBeforeAbsorption, container.getReduction(DamageContainer.Reduction.ABSORPTION));
                entity.setAbsorptionAmount(Math.max(0F, oldAbsorptionAmount - absorbed));
                if (absorbed > 0.0F && absorbed < 3.4028235E37F) {
                    float newDamage = newDamageBeforeAbsorption - absorbed;
                    event.setNewDamage(newDamage);
                }
            }
        }
    }

    @SubscribeEvent
    private static void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //relish mob effect: multiply food values
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
        Level level = entity.level();

        if (!level.isClientSide()) {
            //relish mob effect: fasten consuming foods
            MobEffectInstance relishEffectInstance = entity.getEffect(APMobEffects.RELISH);
            if (relishEffectInstance != null) {
                if (itemStack.is(Tags.Items.FOODS)) {
                    int amplifier = relishEffectInstance.getAmplifier();
                    int duration = event.getDuration();
                    int newDuration = (int) (duration * Math.pow(0.65, amplifier + 1));
                    if (newDuration < 1) {
                        newDuration = 1;
                    }
                    event.setDuration(newDuration);
                }
            }
        }
    }

    @SubscribeEvent
    private static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //relish mob effect: prevent confusion
            if (effectInstance != null && effectInstance.is(MobEffects.CONFUSION)) {
                if (entity.hasEffect(APMobEffects.RELISH)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }
    }

    @SubscribeEvent
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //wither resistance mob effect: neutralize wither
            MobEffectInstance witherInstance = entity.getEffect(MobEffects.WITHER);
            MobEffectInstance witherResistanceInstance = entity.getEffect(APMobEffects.WITHER_RESISTANCE);
            if (witherInstance != null && witherResistanceInstance != null) {
                if (shouldApplyEffectTickThisTick(witherInstance, entity)) {
                    if (shouldApplyEffectTickThisTick(
                            witherInstance.getEffect().value(),
                            getDuration(witherInstance, entity),
                            witherResistanceInstance.getAmplifier()
                    )) {
                        event.setCanceled(true);
                    }
                }
            }

            //poison resistance mob effect: neutralize poison
            MobEffectInstance poisonInstance = entity.getEffect(MobEffects.POISON);
            MobEffectInstance poisonResistanceInstance = entity.getEffect(APMobEffects.POISON_RESISTANCE);
            if (poisonInstance != null && poisonResistanceInstance != null) {
                if (shouldApplyEffectTickThisTick(poisonInstance, entity)) {
                    if (shouldApplyEffectTickThisTick(
                            poisonInstance.getEffect().value(),
                            getDuration(poisonInstance, entity),
                            poisonResistanceInstance.getAmplifier()
                    )) {
                        event.setCanceled(true);
                    }
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
