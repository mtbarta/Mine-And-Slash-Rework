package com.robertx22.mns_cobblemon.core.spells;

import java.util.HashSet;
import java.util.Set;

public class PokemonAttackProperties {

    private static final Set<String> AOE_ABILITIES = new HashSet<>();
    private static final Set<String> RANGED_ABILITIES = new HashSet<>();

    static {
        // Initialize with comprehensive lists
        // AOE Abilities (Contact, Weather, Aura, Spreading)
        AOE_ABILITIES.add("blaze");
        AOE_ABILITIES.add("torrent");
        AOE_ABILITIES.add("overgrow");
        AOE_ABILITIES.add("swarm");
        AOE_ABILITIES.add("static");
        AOE_ABILITIES.add("flame body");
        AOE_ABILITIES.add("pressure");
        AOE_ABILITIES.add("stench");
        AOE_ABILITIES.add("drizzle");
        AOE_ABILITIES.add("drought");
        AOE_ABILITIES.add("sand stream");
        AOE_ABILITIES.add("snow warning");
        AOE_ABILITIES.add("effect spore");
        AOE_ABILITIES.add("intimidate");
        AOE_ABILITIES.add("bad dreams");
        AOE_ABILITIES.add("poison touch");
        AOE_ABILITIES.add("mummy");
        AOE_ABILITIES.add("gooey");
        AOE_ABILITIES.add("cursed body");
        AOE_ABILITIES.add("cotton down");
        AOE_ABILITIES.add("perish body");
        AOE_ABILITIES.add("wandering spirit");
        AOE_ABILITIES.add("neutralizing gas");
        AOE_ABILITIES.add("pastel veil");
        AOE_ABILITIES.add("toxic debris");
        AOE_ABILITIES.add("orichalcum pulse");
        AOE_ABILITIES.add("hadron engine");
        AOE_ABILITIES.add("poison point");
        AOE_ABILITIES.add("cute charm");
        AOE_ABILITIES.add("aftermath");
        AOE_ABILITIES.add("tangling hair");
        AOE_ABILITIES.add("iron barbs");
        AOE_ABILITIES.add("rough skin");
        AOE_ABILITIES.add("unnerve");
        AOE_ABILITIES.add("dark aura");
        AOE_ABILITIES.add("fairy aura");
        AOE_ABILITIES.add("aura break");
        AOE_ABILITIES.add("flower veil");
        AOE_ABILITIES.add("aroma veil");
        AOE_ABILITIES.add("sweet veil");
        AOE_ABILITIES.add("friend guard");

        // Ranged Abilities (Flying, Shooting, Mental, Beam, Pulse)
        RANGED_ABILITIES.add("levitate");
        RANGED_ABILITIES.add("keen eye");
        RANGED_ABILITIES.add("sniper");
        RANGED_ABILITIES.add("compound eyes");
        RANGED_ABILITIES.add("magic guard");
        RANGED_ABILITIES.add("solar power");
        RANGED_ABILITIES.add("blaze");
        RANGED_ABILITIES.add("torrent");
        RANGED_ABILITIES.add("overgrow");
        RANGED_ABILITIES.add("mega launcher");
        RANGED_ABILITIES.add("tinted lens");
        RANGED_ABILITIES.add("liquid voice");
        RANGED_ABILITIES.add("long reach");
        RANGED_ABILITIES.add("punk rock");
        RANGED_ABILITIES.add("propeller tail");
        RANGED_ABILITIES.add("stalwart");
        RANGED_ABILITIES.add("transistor");
        RANGED_ABILITIES.add("dragon's maw");
        RANGED_ABILITIES.add("storm drain");
        RANGED_ABILITIES.add("lightning rod");
        RANGED_ABILITIES.add("magic bounce");
        RANGED_ABILITIES.add("telepathy");
        RANGED_ABILITIES.add("synchronize");
        RANGED_ABILITIES.add("trace");
        RANGED_ABILITIES.add("download");
        RANGED_ABILITIES.add("analytic");
        RANGED_ABILITIES.add("anticipation");
        RANGED_ABILITIES.add("forewarn");
        RANGED_ABILITIES.add("frisk");
        RANGED_ABILITIES.add("gale wings");
        RANGED_ABILITIES.add("big pecks");
        RANGED_ABILITIES.add("aerilate");
        RANGED_ABILITIES.add("pixilate");
        RANGED_ABILITIES.add("refrigerate");
        RANGED_ABILITIES.add("galvanize");
        RANGED_ABILITIES.add("blaster"); // Custom/Modded checks
        RANGED_ABILITIES.add("launcher");
    }

    public static boolean isAoe(String abilityName) {
        if (abilityName == null)
            return false;
        return AOE_ABILITIES.contains(abilityName.toLowerCase());
    }

    public static boolean isRanged(String abilityName) {
        if (abilityName == null)
            return false;
        return RANGED_ABILITIES.contains(abilityName.toLowerCase());
    }
}
