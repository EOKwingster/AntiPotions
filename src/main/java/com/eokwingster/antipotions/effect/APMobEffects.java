package com.eokwingster.antipotions.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APMobEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MODID);

    public static final Holder<MobEffect> VULNERABLE = MOB_EFFECTS.register(
            "vulnerable", () -> new BaseMobEffect(MobEffectCategory.HARMFUL, 16711680)
    );

    public static final Holder<MobEffect> JUMP_LOSS = MOB_EFFECTS.register(
            "jump_loss", () -> new BaseMobEffect(MobEffectCategory.HARMFUL, 5189895)
                    .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath(MODID, "effect.jump_loss.save_fall_distance"), -1, AttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(MODID, "effect.jump_loss.jump_strength"), -0.1, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> RELISH = MOB_EFFECTS.register(
            "relish", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 11199157)
    );

    public static final Holder<MobEffect> VISIBILITY = MOB_EFFECTS.register(
            "visibility", () -> new BaseMobEffect(MobEffectCategory.HARMFUL, 263172)
    );

    public static final Holder<MobEffect> HEAVINESS = MOB_EFFECTS.register(
            "heaviness", () -> new BaseMobEffect(MobEffectCategory.NEUTRAL, 1978438)
                    .addAttributeModifier(Attributes.GRAVITY, ResourceLocation.fromNamespaceAndPath(MODID, "effect.heaviness"), 0.06, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> DARKNESS_RESISTANCE = MOB_EFFECTS.register(
            "darkness_resistance", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 14080222)
    );
    public static final Holder<MobEffect> BLINDNESS_RESISTANCE = MOB_EFFECTS.register(
            "blindness_resistance", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 16514043)
    );
    public static final Holder<MobEffect> POISON_RESISTANCE = MOB_EFFECTS.register(
            "poison_resistance", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 12556751)
    );
    public static final Holder<MobEffect> WITHER_RESISTANCE = MOB_EFFECTS.register(
            "wither_resistance", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 9215657)
    );

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}