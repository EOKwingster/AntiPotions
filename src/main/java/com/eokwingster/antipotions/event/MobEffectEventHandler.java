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
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID)
public class MobEffectEventHandler {
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
                float newDamageBeforeAbsorption = f / 25f;
                //recompute absorption
                float oldAbsorptionAmount = entity.getAbsorptionAmount() + absorption;
                container.setReduction(DamageContainer.Reduction.ABSORPTION, Math.min(oldAbsorptionAmount, newDamageBeforeAbsorption));
                float absorbed = Math.min(newDamageBeforeAbsorption, container.getReduction(DamageContainer.Reduction.ABSORPTION));
                entity.setAbsorptionAmount(Math.max(0, oldAbsorptionAmount - absorbed));
                if (absorbed > 0.0f && absorbed < 3.4028235E37F) {
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
                if (event.getEntity().hasEffect(APMobEffects.RELISH)) {
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
