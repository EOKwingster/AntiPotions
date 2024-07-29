package com.eokwingster.antipotions.event;

import com.eokwingster.antipotions.potion.APPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

@EventBusSubscriber(modid = MODID)
public class BrewingRecipes {
    @SubscribeEvent
    private static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addStartMix(Items.WITHER_ROSE, APPotions.WITHER_RESISTANCE);
        builder.addMix(APPotions.WITHER_RESISTANCE, Items.REDSTONE, APPotions.LONG_WITHER_RESISTANCE);

        builder.addStartMix(Items.HONEYCOMB, APPotions.STICKY_LAND);
        builder.addMix(APPotions.STICKY_LAND, Items.REDSTONE, APPotions.LONG_STICKY_LAND);
        builder.addMix(APPotions.STICKY_LAND, Items.GLOWSTONE_DUST, APPotions.STRONG_STICKY_LAND);

        builder.addStartMix(Items.GLOW_BERRIES, APPotions.RELISH);
        builder.addMix(APPotions.RELISH, Items.REDSTONE, APPotions.LONG_RELISH);
        builder.addMix(APPotions.RELISH, Items.GLOWSTONE_DUST, APPotions.STRONG_RELISH);

        builder.addMix(Potions.AWKWARD, Items.ECHO_SHARD, APPotions.THIEF);
        builder.addMix(APPotions.THIEF, Items.REDSTONE, APPotions.LONG_THIEF);
        builder.addMix(APPotions.THIEF, Items.GLOWSTONE_DUST, APPotions.STRONG_THIEF);

        builder.addMix(Potions.NIGHT_VISION, Items.ENDER_EYE, APPotions.VISIBILITY);
        builder.addMix(Potions.LONG_NIGHT_VISION, Items.ENDER_EYE, APPotions.LONG_VISIBILITY);
        builder.addMix(APPotions.VISIBILITY, Items.REDSTONE, APPotions.LONG_VISIBILITY);

        builder.addStartMix(Items.POISONOUS_POTATO, APPotions.POISON_RESISTANCE);
        builder.addMix(APPotions.POISON_RESISTANCE, Items.REDSTONE, APPotions.LONG_POISON_RESISTANCE);

        builder.addMix(Potions.NIGHT_VISION, Items.GOLDEN_CARROT, APPotions.SIGHT);
        builder.addMix(APPotions.SIGHT, Items.REDSTONE, APPotions.LONG_SIGHT);
        builder.addMix(Potions.LONG_NIGHT_VISION, Items.GOLDEN_CARROT, APPotions.LONG_SIGHT);

        builder.addStartMix(Items.POPPED_CHORUS_FRUIT, APPotions.HEAVINESS);
        builder.addMix(APPotions.HEAVINESS, Items.REDSTONE, APPotions.LONG_HEAVINESS);
        builder.addMix(APPotions.HEAVINESS, Items.RABBIT_FOOT, APPotions.STEADINESS);
        builder.addMix(APPotions.LONG_HEAVINESS, Items.RABBIT_FOOT, APPotions.LONG_STEADINESS);
        builder.addMix(Potions.LEAPING, Items.POPPED_CHORUS_FRUIT, APPotions.STEADINESS);
        builder.addMix(Potions.LONG_LEAPING, Items.POPPED_CHORUS_FRUIT, APPotions.LONG_STEADINESS);
    }
}
