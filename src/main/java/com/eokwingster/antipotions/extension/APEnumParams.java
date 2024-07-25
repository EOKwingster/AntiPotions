package com.eokwingster.antipotions.extension;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import static com.eokwingster.antipotions.AntiPotionsMod.MODID;

public class APEnumParams {
    //HeartType
    public static final EnumProxy<Gui.HeartType> ANTI_WITHERED_HEART_TYPE = new EnumProxy<>(
            Gui.HeartType.class,
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_half_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_hardcore_full"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_hardcore_full_blinking"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_hardcore_half"),
            ResourceLocation.fromNamespaceAndPath(MODID, "hud/heart/anti_withered_hardcore_half_blinking")
    );
}
