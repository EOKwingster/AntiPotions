package com.eokwingster.antipotions.potion;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APPotions {
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, MODID);

    public static final Holder<Potion> JUMP_LOSS = POTIONS.register(
            "jump_loss", () -> new Potion(
                    "jump_loss", new MobEffectInstance(APMobEffects.JUMP_LOSS, 3600)
            )
    );
    public static final Holder<Potion> LONG_JUMP_LOSS = POTIONS.register(
            "long_jump_loss", () -> new Potion(
                    "jump_loss", new MobEffectInstance(APMobEffects.JUMP_LOSS, 9600)
            )
    );
    public static final Holder<Potion> STRONG_JUMP_LOSS = POTIONS.register(
            "strong_jump_loss", () -> new Potion(
                    "jump_loss", new MobEffectInstance(APMobEffects.JUMP_LOSS, 1800, 1)
            )
    );

    public static final Holder<Potion> ANTI_WITHER = POTIONS.register(
            "anti_wither", () -> new Potion(
                    "anti_wither", new MobEffectInstance(APMobEffects.ANTI_WITHER, 3600)
            )
    );
    public static final Holder<Potion> LONG_ANTI_WITHER = POTIONS.register(
            "long_anti_wither", () -> new Potion(
                    "anti_wither", new MobEffectInstance(APMobEffects.ANTI_WITHER, 9600)
            )
    );
    public static final Holder<Potion> STRONG_ANTI_WITHER = POTIONS.register(
            "strong_anti_wither", () -> new Potion(
                    "anti_wither", new MobEffectInstance(APMobEffects.ANTI_WITHER, 1800, 1)
            )
    );

    public static void register(IEventBus modEventBus) {
        POTIONS.register(modEventBus);
    }
}
