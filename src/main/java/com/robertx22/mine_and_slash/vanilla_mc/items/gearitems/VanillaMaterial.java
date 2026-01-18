package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems;

import net.minecraft.core.Holder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
// import net.minecraft.world.item.ArmorItem;

public enum VanillaMaterial {

    WOOD("wood", new ItemOrTag(ItemTags.PLANKS), ArmorMaterials.LEATHER, Tiers.WOOD, 80), // Leather: 5 * 16
    IRON("iron", new ItemOrTag(Items.IRON_INGOT), ArmorMaterials.IRON, Tiers.IRON, 240), // Iron: 15 * 16
    GOLD("gold", new ItemOrTag(Items.GOLD_INGOT), ArmorMaterials.GOLD, Tiers.GOLD, 112), // Gold: 7 * 16
    DIAMOND("diamond", new ItemOrTag(Items.DIAMOND), ArmorMaterials.DIAMOND, Tiers.DIAMOND, 528); // Diamond: 33 * 16

    // NeoForge 1.21: Creating ArmorItem instances during mod init creates intrusive
    // holders
    // that must be registered. Use pre-calculated durability values instead.
    // Formula: ArmorType base (16 for chestplate) × ArmorMaterial durability
    // multiplier
    private final int chestplateDurability;

    public int getChestplateDurability() {
        return chestplateDurability;
    }

    public String id;
    public ItemOrTag mat;
    public Holder<ArmorMaterial> armormat;
    public Tiers toolmat;

    VanillaMaterial(String id, ItemOrTag mat, Holder<ArmorMaterial> armormat, Tiers toolmat, int chestplateDurability) {
        this.id = id;
        this.mat = mat;
        this.armormat = armormat;
        this.toolmat = toolmat;
        this.chestplateDurability = chestplateDurability;
    }

    public static class ItemOrTag {
        public Item item;
        public TagKey<Item> tag;

        public ItemOrTag(Item item) {
            this.item = item;
        }

        public ItemOrTag(TagKey<Item> tag) {
            this.tag = tag;
        }
    }
}
