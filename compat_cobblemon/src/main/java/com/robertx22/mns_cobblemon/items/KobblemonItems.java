package com.robertx22.mns_cobblemon.items;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.mns_cobblemon.MnSCobblemonCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class KobblemonItems {

        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM,
                        MnSCobblemonCompat.MODID);

        // ===== HELD ITEMS =====
        // Choice items
        public static final Supplier<Item> CHOICE_BAND = ITEMS.register("choice_band",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> CHOICE_SPECS = ITEMS.register("choice_specs",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> CHOICE_SCARF = ITEMS.register("choice_scarf",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Sustain items
        public static final Supplier<Item> LEFTOVERS = ITEMS.register("leftovers",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> SHELL_BELL = ITEMS.register("shell_bell",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Damage items
        public static final Supplier<Item> LIFE_ORB = ITEMS.register("life_orb",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> EXPERT_BELT = ITEMS.register("expert_belt",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Defensive items
        public static final Supplier<Item> FOCUS_SASH = ITEMS.register("focus_sash",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> ASSAULT_VEST = ITEMS.register("assault_vest",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> ROCKY_HELMET = ITEMS.register("rocky_helmet",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // ===== BATTLE ITEMS =====
        // Critical hit items
        public static final Supplier<Item> SCOPE_LENS = ITEMS.register("scope_lens",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> RAZOR_CLAW = ITEMS.register("razor_claw",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Accuracy/Priority items
        public static final Supplier<Item> WIDE_LENS = ITEMS.register("wide_lens",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> QUICK_CLAW = ITEMS.register("quick_claw",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Type-boosting items (one for each Pokemon type)
        public static final Supplier<Item> CHARCOAL = ITEMS.register("charcoal",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> MYSTIC_WATER = ITEMS.register("mystic_water",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> MIRACLE_SEED = ITEMS.register("miracle_seed",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> MAGNET = ITEMS.register("magnet",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> NEVER_MELT_ICE = ITEMS.register("never_melt_ice",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> BLACK_BELT = ITEMS.register("black_belt",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POISON_BARB = ITEMS.register("poison_barb",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> SOFT_SAND = ITEMS.register("soft_sand",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> SHARP_BEAK = ITEMS.register("sharp_beak",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> TWISTED_SPOON = ITEMS.register("twisted_spoon",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> SILVER_POWDER = ITEMS.register("silver_powder",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> HARD_STONE = ITEMS.register("hard_stone",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> SPELL_TAG = ITEMS.register("spell_tag",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> DRAGON_FANG = ITEMS.register("dragon_fang",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> BLACK_GLASSES = ITEMS.register("black_glasses",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> METAL_COAT = ITEMS.register("metal_coat",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> FAIRY_FEATHER = ITEMS.register("fairy_feather",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // ===== TRAINING ITEMS =====
        // Power items (EV training)
        public static final Supplier<Item> POWER_WEIGHT = ITEMS.register("power_weight",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POWER_BRACER = ITEMS.register("power_bracer",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POWER_BELT = ITEMS.register("power_belt",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POWER_LENS = ITEMS.register("power_lens",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POWER_BAND = ITEMS.register("power_band",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> POWER_ANKLET = ITEMS.register("power_anklet",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Experience items
        public static final Supplier<Item> LUCKY_EGG = ITEMS.register("lucky_egg",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> EXP_SHARE = ITEMS.register("exp_share",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // Breeding/Friendship items
        public static final Supplier<Item> SOOTHE_BELL = ITEMS.register("soothe_bell",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // ===== MEGA STONES =====
        public static final Supplier<Item> MEGA_STONE = ITEMS.register("mega_stone",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> Z_CRYSTAL = ITEMS.register("z_crystal",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));
        public static final Supplier<Item> TERA_ORB = ITEMS.register("tera_orb",
                        () -> new ItemRing(VanillaMaterial.DIAMOND));

        // ===== CURRENCY =====
        public static final Supplier<Item> COBBLEMON_ESSENCE = ITEMS.register("cobblemon_essence",
                        () -> new Item(new Item.Properties()));
}
