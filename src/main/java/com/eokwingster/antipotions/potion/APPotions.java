package com.eokwingster.antipotions.potion;

import com.eokwingster.antipotions.effect.APMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APPotions {
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, MODID);

    public static final Holder<Potion> STEADINESS = POTIONS.register(
            "steadiness", () -> new Potion(
                    "steadiness",
                    new MobEffectInstance(APMobEffects.HEAVINESS, 3600),
                    new MobEffectInstance(MobEffects.JUMP, 3600, 1)
            )
    );
    public static final Holder<Potion> LONG_STEADINESS = POTIONS.register(
            "long_steadiness", () -> new Potion(
                    "steadiness",
                    new MobEffectInstance(APMobEffects.HEAVINESS, 9600),
                    new MobEffectInstance(MobEffects.JUMP, 9600, 1)
            )
    );
    public static final Holder<Potion> HEAVINESS = POTIONS.register(
            "heaviness", () -> new Potion(
                    "heaviness",
                    new MobEffectInstance(APMobEffects.HEAVINESS, 3600)
            )
    );
    public static final Holder<Potion> LONG_HEAVINESS = POTIONS.register(
            "long_heaviness", () -> new Potion(
                    "heaviness",
                    new MobEffectInstance(APMobEffects.HEAVINESS, 9600)
            )
    );
    
    public static final Holder<Potion> SIGHT = POTIONS.register(
            "sight", () -> new Potion(
                    "sight",
                    new MobEffectInstance(MobEffects.NIGHT_VISION, 3600),
                    new MobEffectInstance(APMobEffects.BLINDNESS_RESISTANCE, 3600),
                    new MobEffectInstance(APMobEffects.DARKNESS_RESISTANCE, 3600, 0, false, false)
            )
    );
    public static final Holder<Potion> LONG_SIGHT = POTIONS.register(
            "long_sight", () -> new Potion(
                    "sight",
                    new MobEffectInstance(MobEffects.NIGHT_VISION, 9600),
                    new MobEffectInstance(APMobEffects.BLINDNESS_RESISTANCE, 9600),
                    new MobEffectInstance(APMobEffects.DARKNESS_RESISTANCE, 9600, 0, false, false)
            )
    );

    public static final Holder<Potion> POISON_RESISTANCE = POTIONS.register(
            "poison_resistance", () -> new Potion(
                    "poison_resistance", new MobEffectInstance(APMobEffects.POISON_RESISTANCE, 900)
            )
    );
    public static final Holder<Potion> LONG_POISON_RESISTANCE = POTIONS.register(
            "long_poison_resistance", () -> new Potion(
                    "poison_resistance", new MobEffectInstance(APMobEffects.POISON_RESISTANCE, 1800)
            )
    );
    
    public static final Holder<Potion> VISIBILITY = POTIONS.register(
            "visibility", () -> new Potion(
                    "visibility",
                    new MobEffectInstance(APMobEffects.VISIBILITY, 3600),
                    new MobEffectInstance(MobEffects.GLOWING, 3600)
            )
    );
    public static final Holder<Potion> LONG_VISIBILITY = POTIONS.register(
            "long_visibility", () -> new Potion(
                    "visibility",
                    new MobEffectInstance(APMobEffects.VISIBILITY, 9600),
                    new MobEffectInstance(MobEffects.GLOWING, 9600)
            )
    );

    public static final Holder<Potion> THIEF = POTIONS.register(
            "thief", () -> new Potion(
                    "thief",
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 3),
                    new MobEffectInstance(MobEffects.INVISIBILITY, 200),
                    new MobEffectInstance(APMobEffects.VULNERABLE, 400, 2)
            )
    );
    public static final Holder<Potion> LONG_THIEF = POTIONS.register(
            "long_thief", () -> new Potion(
                    "thief",
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 800, 3),
                    new MobEffectInstance(MobEffects.INVISIBILITY, 400),
                    new MobEffectInstance(APMobEffects.VULNERABLE, 800, 2)
            )
    );
    public static final Holder<Potion> STRONG_THIEF = POTIONS.register(
            "strong_thief", () -> new Potion(
                    "thief",
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 5),
                    new MobEffectInstance(MobEffects.INVISIBILITY, 200),
                    new MobEffectInstance(APMobEffects.VULNERABLE, 400, 3)
            )
    );

    public static final Holder<Potion> RELISH = POTIONS.register(
            "relish", () -> new Potion(
                    "relish", new MobEffectInstance(APMobEffects.RELISH, 3600)
            )
    );
    public static final Holder<Potion> LONG_RELISH = POTIONS.register(
            "long_relish", () -> new Potion(
                    "relish", new MobEffectInstance(APMobEffects.RELISH, 9600)
            )
    );
    public static final Holder<Potion> STRONG_RELISH = POTIONS.register(
            "strong_relish", () -> new Potion(
                    "relish", new MobEffectInstance(APMobEffects.RELISH, 1800, 1)
            )
    );
    
    public static final Holder<Potion> STICKY_LAND = POTIONS.register(
            "sticky_land", () -> new Potion(
                    "sticky_land", new MobEffectInstance(APMobEffects.JUMP_LOSS, 3600)
            )
    );
    public static final Holder<Potion> LONG_STICKY_LAND = POTIONS.register(
            "long_sticky_land", () -> new Potion(
                    "sticky_land", new MobEffectInstance(APMobEffects.JUMP_LOSS, 9600)
            )
    );
    public static final Holder<Potion> STRONG_STICKY_LAND = POTIONS.register(
            "strong_sticky_land", () -> new Potion(
                    "sticky_land", new MobEffectInstance(APMobEffects.JUMP_LOSS, 1800, 1)
            )
    );

    public static final Holder<Potion> WITHER_RESISTANCE = POTIONS.register(
            "wither_resistance", () -> new Potion(
                    "wither_resistance", new MobEffectInstance(APMobEffects.WITHER_RESISTANCE, 3600)
            )
    );
    public static final Holder<Potion> LONG_WITHER_RESISTANCE = POTIONS.register(
            "long_wither_resistance", () -> new Potion(
                    "wither_resistance", new MobEffectInstance(APMobEffects.WITHER_RESISTANCE, 9600)
            )
    );

    public static void register(IEventBus modEventBus) {
        POTIONS.register(modEventBus);
    }
}
