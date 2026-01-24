package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.capability.player.container.BackpackScreen;
import com.robertx22.mine_and_slash.capability.player.container.JewelScreen;
import com.robertx22.mine_and_slash.capability.player.container.SkillGemsScreen;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.screen.*;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ContainerGuiRegisters {

    public static void reg() {

        ForgeEvents.registerForgeEvent(RegisterMenuScreensEvent.class, event -> {
            event.register(SlashContainers.STATIONS.get(Professions.SALVAGING).get(), SalvagingScreen::new);
            event.register(SlashContainers.STATIONS.get(Professions.GEAR_CRAFTING).get(), GearCraftingScreen::new);
            event.register(SlashContainers.STATIONS.get(Professions.ALCHEMY).get(), AlchemyScreen::new);
            event.register(SlashContainers.STATIONS.get(Professions.INFUSING).get(), InfusingScreen::new);
            event.register(SlashContainers.STATIONS.get(Professions.COOKING).get(), CookingScreen::new);

            event.register(SlashContainers.SKILL_GEMS.get(), SkillGemsScreen::new);

            for (var tab : SlashContainers.BACKPACK_TABS.values()) {
                event.register(tab.get(), BackpackScreen::new);
            }

            event.register(SlashContainers.JEWEL.get(), JewelScreen::new);
        });

    }

}
