package com.eokwingster.antipotions.extension;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APEnumParams {
    //HeartTypes
    public static final EnumProxy<Gui.HeartType> WITHER_RESISTANCE_HEART_TYPE = new EnumProxy<>(
            Gui.HeartType.class,
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_half_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_hardcore_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_hardcore_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_hardcore_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/wither_resistance_hardcore_half_blinking")
    );
    public static final EnumProxy<Gui.HeartType> POISON_RESISTANCE_HEART_TYPE = new EnumProxy<>(
            Gui.HeartType.class,
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_half_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_hardcore_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_hardcore_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_hardcore_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/poison_resistance_hardcore_half_blinking")
    );
}
