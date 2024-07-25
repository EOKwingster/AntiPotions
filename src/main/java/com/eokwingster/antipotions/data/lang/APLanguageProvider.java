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
        add(APMobEffects.ANTI_WITHER.value(), "Anti Wither");
        addPotion(APPotions.ANTI_WITHER, "Anti Wither");
    }

    private void addPotion(Holder<Potion> potionHolder, String name) {
        add(potionLanguageKey(potionHolder, Items.TIPPED_ARROW), "Arrow of " + name);
        add(potionLanguageKey(potionHolder, Items.POTION), "Potion of " + name);
        add(potionLanguageKey(potionHolder, Items.SPLASH_POTION), "Splash Potion of " + name);
        add(potionLanguageKey(potionHolder, Items.LINGERING_POTION), "Lingering Potion of " + name);
    }

    private String potionLanguageKey(Holder<Potion> potionHolder, Item item) {
        return Potion.getName(Optional.of(potionHolder), BuiltInRegistries.ITEM.getKey(item).toLanguageKey("item", "effect."));
    }
}
