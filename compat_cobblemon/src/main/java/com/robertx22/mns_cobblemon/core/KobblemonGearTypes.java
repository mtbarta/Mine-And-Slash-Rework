package com.robertx22.mns_cobblemon.core;

import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

import java.util.ArrayList;
import java.util.List;

public class KobblemonGearTypes implements ExileRegistryInit {

    public static List<DataGenKey<BaseGearType>> ALL = new ArrayList<>();

    // Held Items (standard battle items)
    public final static DataGenKey<BaseGearType> POKEMON_HELD_ITEM = of("pokemon_held_item");

    // Battle Items (combat-focused)
    public final static DataGenKey<BaseGearType> POKEMON_BATTLE_ITEM = of("pokemon_battle_item");

    // Training Items (stat boosting)
    public final static DataGenKey<BaseGearType> POKEMON_TRAINING_ITEM = of("pokemon_training_item");

    // Mega Stones (transformation items)
    public final static DataGenKey<BaseGearType> POKEMON_MEGA_STONE = of("pokemon_mega_stone");

    @Override
    public void registerAll() {
        // ===== HELD ITEMS =====
        BaseGearType heldType = new BaseGearType(KobblemonGearSlots.POKEMON_HELD, POKEMON_HELD_ITEM.GUID(),
                "Held Item");

        // Choice items (boost one stat)
        heldType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:choice_band", "common"));
        heldType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:choice_specs", "common"));
        heldType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:choice_scarf", "common"));

        // Sustain items
        heldType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:leftovers", "common"));
        heldType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:shell_bell", "uncommon"));

        // Damage items
        heldType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:life_orb", "uncommon"));
        heldType.possible_items.add(new BaseGearType.ItemChance(60, "mns_cobblemon:expert_belt", "rare"));

        // Defensive items
        heldType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:focus_sash", "uncommon"));
        heldType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:assault_vest", "uncommon"));
        heldType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:rocky_helmet", "uncommon"));

        heldType.addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // ===== BATTLE ITEMS =====
        BaseGearType battleType = new BaseGearType(KobblemonGearSlots.POKEMON_BATTLE, POKEMON_BATTLE_ITEM.GUID(),
                "Battle Item");

        // Critical hit items
        battleType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:scope_lens", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:razor_claw", "uncommon"));

        // Accuracy/Priority items
        battleType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:wide_lens", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:quick_claw", "common"));

        // Type-boosting items
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:charcoal", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:mystic_water", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:miracle_seed", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:magnet", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:never_melt_ice", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:black_belt", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:poison_barb", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:soft_sand", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:sharp_beak", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:twisted_spoon", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:silver_powder", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:hard_stone", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:spell_tag", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:dragon_fang", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:black_glasses", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:metal_coat", "common"));
        battleType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:fairy_feather", "common"));

        battleType.addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // ===== TRAINING ITEMS =====
        BaseGearType trainingType = new BaseGearType(KobblemonGearSlots.POKEMON_TRAINING, POKEMON_TRAINING_ITEM.GUID(),
                "Training Item");

        // Power items (EV training)
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_weight", "common"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_bracer", "common"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_belt", "common"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_lens", "common"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_band", "common"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(100, "mns_cobblemon:power_anklet", "common"));

        // Experience items
        trainingType.possible_items.add(new BaseGearType.ItemChance(60, "mns_cobblemon:lucky_egg", "rare"));
        trainingType.possible_items.add(new BaseGearType.ItemChance(60, "mns_cobblemon:exp_share", "rare"));

        // Breeding/Friendship items
        trainingType.possible_items.add(new BaseGearType.ItemChance(80, "mns_cobblemon:soothe_bell", "uncommon"));

        trainingType.addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // ===== MEGA STONES =====
        BaseGearType megaType = new BaseGearType(KobblemonGearSlots.POKEMON_MEGA, POKEMON_MEGA_STONE.GUID(),
                "Mega Stone");

        // Generic mega stones (species-specific ones would be added later)
        megaType.possible_items.add(new BaseGearType.ItemChance(40, "mns_cobblemon:mega_stone", "epic"));
        megaType.possible_items.add(new BaseGearType.ItemChance(20, "mns_cobblemon:z_crystal", "legendary"));
        megaType.possible_items.add(new BaseGearType.ItemChance(30, "mns_cobblemon:tera_orb", "epic"));

        megaType.addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
    }

    static DataGenKey<BaseGearType> of(String id) {
        var b = new DataGenKey<BaseGearType>(id);
        ALL.add(b);
        return b;
    }
}
