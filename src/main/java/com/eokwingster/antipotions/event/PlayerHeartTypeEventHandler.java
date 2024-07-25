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

        //anti_withered
        if (!event.getType().equals(Gui.HeartType.POISIONED)) {
            MobEffectInstance antiWitherInstance = player.getEffect(APMobEffects.ANTI_WITHER);
            MobEffectInstance witherInstance = player.getEffect(MobEffects.WITHER);
            if (antiWitherInstance != null) {
                if (witherInstance == null || witherInstance.getAmplifier() <= antiWitherInstance.getAmplifier()) {
                    event.setType(APEnumParams.ANTI_WITHERED_HEART_TYPE.getValue());
                }
            }
        }
    }
}
