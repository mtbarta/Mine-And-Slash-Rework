package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cached {

    // In NeoForge 1.21, AttributeModifier uses ResourceLocation instead of UUID
    public static List<ImmutablePair<Attribute, ResourceLocation>> VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();
    public static HashMap<String, Integer> MAX_SPELL_CHARGES = new HashMap<>();

}
