package com.eokwingster.antipotions.data.lang;

import com.eokwingster.antipotions.effect.APMobEffects;
import com.eokwingster.antipotions.potion.APPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Optional;

public class APLanguageProvider extends LanguageProvider {
    public APLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        //mob effects
        add(APMobEffects.VULNERABLE.value(), "Vulnerable");
        add(APMobEffects.JUMP_LOSS.value(), "Jump Loss");
        add(APMobEffects.RELISH.value(), "Relish");
        add(APMobEffects.VISIBILITY.value(), "Visibility");
        add(APMobEffects.WITHER_RESISTANCE.value(), "Wither Resistance");
        add(APMobEffects.POISON_RESISTANCE.value(), "Poison Resistance");
        add(APMobEffects.BLINDNESS_RESISTANCE.value(), "Blindness Resistance");
        add(APMobEffects.DARKNESS_RESISTANCE.value(), "Darkness Resistance");
        add(APMobEffects.HEAVINESS.value(), "Heaviness");

        //potions
        addPotions(APPotions.THIEF, "the Thief");
        addPotions(APPotions.STICKY_LAND, "Sticky Land");
        addPotions(APPotions.RELISH, "Relish");
        addPotions(APPotions.VISIBILITY, "Visibility");
        addPotions(APPotions.SIGHT, "Sight");
        addPotions(APPotions.WITHER_RESISTANCE, "Wither Resistance");
        addPotions(APPotions.POISON_RESISTANCE, "Poison Resistance");
        addPotions(APPotions.HEAVINESS, "Heaviness");
        addPotions(APPotions.STEADINESS, "Steadiness");
    }

    private void addPotions(Holder<Potion> potionHolder, String name) {
        add(potionLanguageKey(potionHolder, Items.TIPPED_ARROW), "Arrow of " + name);
        add(potionLanguageKey(potionHolder, Items.POTION), "Potion of " + name);
        add(potionLanguageKey(potionHolder, Items.SPLASH_POTION), "Splash Potion of " + name);
        add(potionLanguageKey(potionHolder, Items.LINGERING_POTION), "Lingering Potion of " + name);
    }

    private String potionLanguageKey(Holder<Potion> potionHolder, Item item) {
        return Potion.getName(Optional.of(potionHolder), BuiltInRegistries.ITEM.getKey(item).toLanguageKey("item", "effect."));
    }
}
