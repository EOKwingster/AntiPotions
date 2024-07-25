package com.eokwingster.antipotions;

import com.eokwingster.antipotions.effect.APMobEffects;
import com.eokwingster.antipotions.potion.APPotions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(AntiPotionsMod.MODID)
public class AntiPotionsMod
{
    public static final String MODID = "antipotions";

    public AntiPotionsMod(IEventBus bus) {
        APMobEffects.register(bus);
        APPotions.register(bus);
    }
}
