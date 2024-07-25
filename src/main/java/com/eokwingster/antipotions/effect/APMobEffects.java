package com.eokwingster.antipotions.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APMobEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MODID);

    public static final Holder<MobEffect> ANTI_WITHER = MOB_EFFECTS.register(
            "anti_wither", () -> new BaseMobEffect(MobEffectCategory.BENEFICIAL, 9215657)
    );

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}
