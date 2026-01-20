package com.robertx22.mns_minecolonies;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class MineColoniesIntegration {

    public static void register() {
        ForgeEvents.registerForgeEvent(EntityJoinLevelEvent.class, event -> {
            if (event.getEntity() instanceof AbstractEntityCitizen citizen) {
                if (!event.getLevel().isClientSide) {
                    handleCitizen(citizen);
                }
            }
        });
    }

    private static void handleCitizen(AbstractEntityCitizen citizen) {
        // Extensible job check could go here
        // For now, let's just assume we want to apply it if they are guards
        // But the job API might be complex to check directly without looking up
        // registry names
        // Ideally we check if the job name contains "guard" or "knight" etc.

        try {
            if (citizen.getCitizenData() != null && citizen.getCitizenData().getJob() != null) {
                if (citizen.getCitizenData().getJob().isGuard()) {
                    applyWizardGuard(citizen);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void applyWizardGuard(AbstractEntityCitizen citizen) {
        EntityData data = Load.Unit(citizen);
        if (data != null) {
            // Only set if not already set or if we want to force it?
            // Usually we only set it once on fresh spawn or specific updates
            // But setRarity might reset stats, so be careful.
            // EntityData handles "needsToBeGivenStats" which might help.

            if (data.needsToBeGivenStats() || !data.getRarity().equals("mns_minecolonies:wizard_guard")) {
                data.setRarity("mns_minecolonies:wizard_guard");
                // Trigger stat recalc happens naturally when setRarity calls setDirty and on
                // tick
            }
        }
    }
}
