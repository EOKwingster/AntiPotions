package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.tags.DamageTypeTags;
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
    private static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();

        //relish mob effect: prevent nausea
        if (effectInstance != null && effectInstance.is(MobEffects.CONFUSION)) {
            if (entity.hasEffect(APMobEffects.RELISH)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }

        //blindness resistance mob effect: prevent blindness
        if (effectInstance != null && effectInstance.is(MobEffects.BLINDNESS)) {
            if (entity.hasEffect(APMobEffects.BLINDNESS_RESISTANCE)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }

        //darkness resistance mob effect: prevent darkness
        if (effectInstance != null && effectInstance.is(MobEffects.DARKNESS)) {
            if (entity.hasEffect(APMobEffects.DARKNESS_RESISTANCE)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }

        //heaviness mob effect: prevent levitation
        if (effectInstance != null && effectInstance.is(MobEffects.LEVITATION)) {
            if (entity.hasEffect(APMobEffects.HEAVINESS)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    @SubscribeEvent
    private static void mobEffectAdded(MobEffectEvent.Added event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            //relish mob effect: cancel nausea
            if (effectInstance.is(APMobEffects.RELISH)) {
                entity.removeEffect(MobEffects.CONFUSION);
            }

            //blindness resistance mob effect: cancel blindness
            if (effectInstance.is(APMobEffects.BLINDNESS_RESISTANCE)) {
                entity.removeEffect(MobEffects.BLINDNESS);
            }

            //darkness resistance mob effect: cancel darkness
            if (effectInstance.is(APMobEffects.DARKNESS_RESISTANCE)) {
                entity.removeEffect(MobEffects.DARKNESS);
            }

            //heaviness mob effect: cancel levitation
            if (effectInstance.is(APMobEffects.HEAVINESS)) {
                entity.removeEffect(MobEffects.LEVITATION);
            }
        }
    }

    @SubscribeEvent
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        DamageSource damageSource = event.getSource();

        if (!level.isClientSide()) {
            //wither resistance mob effect: neutralize wither
            MobEffectInstance witherResistanceInstance = entity.getEffect(APMobEffects.WITHER_RESISTANCE);
            if (damageSource.is(Tags.DamageTypes.IS_WITHER) && witherResistanceInstance != null) {
                event.setCanceled(true);
            }

            //poison resistance mob effect: neutralize poison
            if (damageSource.is(Tags.DamageTypes.IS_POISON) && entity.hasEffect(APMobEffects.POISON_RESISTANCE)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    private static void LivingDamagePre(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        DamageSource damageSource = event.getSource();

        if (!level.isClientSide()) {
            //vulnerable mob effect: multiply damage by potionLevel * 0.2
            MobEffectInstance vulnerableEffectInstance = entity.getEffect(APMobEffects.VULNERABLE);
            if (vulnerableEffectInstance != null && !damageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
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
                //deal damage
                float newDamage = newDamageBeforeAbsorption - absorbed;
                event.setNewDamage(newDamage);
            }
        }
    }
}
