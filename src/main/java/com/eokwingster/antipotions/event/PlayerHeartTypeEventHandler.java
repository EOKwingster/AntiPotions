package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.effect.APMobEffects;
import com.eokwingster.antipotions.extension.APEnumParams;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class PlayerHeartTypeEventHandler {
    @SubscribeEvent
    private static void playerHeartType(PlayerHeartTypeEvent event) {
        Player player = event.getEntity();

        //wither resistance
        MobEffectInstance antiWitherInstance = player.getEffect(APMobEffects.WITHER_RESISTANCE);
        MobEffectInstance witherInstance = player.getEffect(MobEffects.WITHER);
        if (antiWitherInstance != null) {
            if (witherInstance == null || witherInstance.getAmplifier() <= antiWitherInstance.getAmplifier()) {
                event.setType(APEnumParams.WITHER_RESISTANCE_HEART_TYPE.getValue());
            }
        }

        //poison resistance
        MobEffectInstance poisonResistanceInstance = player.getEffect(APMobEffects.POISON_RESISTANCE);
        MobEffectInstance poisonInstance = player.getEffect(MobEffects.POISON);
        if (poisonResistanceInstance != null) {
            if (poisonInstance == null || poisonInstance.getAmplifier() <= poisonResistanceInstance.getAmplifier()) {
                event.setType(APEnumParams.POISON_RESISTANCE_HEART_TYPE.getValue());
            }
        }
    }
}
