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

    public static final Holder<MobEffect> RELISH = MOB_EFFECTS.register(
            "relish", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 11199157)
    );

    public static final Holder<MobEffect> JUMP_LOSS = MOB_EFFECTS.register(
            "jump_loss", () -> new BaseMobEffect(MobEffectCategory.HARMFUL, 131195)
                    .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath(MODID, "effect.jump_loss.save_fall_distance"), -1, AttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(MODID, "effect.jump_loss.jump_strength"), -0.1, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> ANTI_WITHER = MOB_EFFECTS.register(
            "anti_wither", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 9215657)
    );

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}