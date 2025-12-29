package com.robertx22.mine_and_slash.a_libraries.curios;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.omen.OmenItem;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemRing;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CurioEvents {

    private static final List<String> id = List.of("jewelry/necklace", "jewelry/ring", "omen");

    public static void reg() {
        ForgeEvents.registerForgeEvent(CurioChangeEvent.class, event -> {

            LivingEntity entity = event.getEntity();
            if (entity != null) {
                if (!entity.level().isClientSide) {
                    EntityData data = Load.Unit(entity);
                    if (data != null) {
                        data.setEquipsChanged();
                    }
                }
            }

        });
    }

    // Note: In NeoForge, ICurio capabilities are registered differently.
    // Items that need Curio functionality should implement ICurioItem directly.
    // The ItemNecklace, ItemRing, and OmenItem classes should implement ICurioItem
    // to provide Curio functionality. This method is kept for reference but may
    // no longer be needed if items properly implement ICurioItem.
    // 
    // If you need to register items programmatically, use CuriosApi.registerCurio()
    // during mod initialization instead of AttachCapabilitiesEvent.

}
