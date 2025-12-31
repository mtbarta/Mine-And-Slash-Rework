package com.robertx22.library_of_exile.database.affix.base;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.ApiForgeEvents;
import net.minecraft.world.entity.LivingEntity;

public class MobAffixEvents {

    public static void init() {

        // mob on spawn affix should be enough like this
        // player affixes need more work, i'll leave that for later

        ApiForgeEvents.registerForgeEvent(EntityTickEvent.Post.class, event -> {
            try {
                if (!(event.getEntity() instanceof LivingEntity))
                    return;
                LivingEntity en = (LivingEntity) event.getEntity();
                if (en.level().isClientSide) {
                    return;
                }
                if (en.tickCount == 3) {
                    var e = new GrabMobAffixesEvent(en);
                    ExileEvents.GRAB_MOB_AFFIXES.callEvents(e);

                    for (ExileAffixData data : e.allAffixes) {
                        data.getAffix().getApplyStrategy().applyOnMobSpawn(data, en);
                    }
                } else {
                    if (en.tickCount % 20 == 0) {
                        var e = new GrabMobAffixesEvent(en);
                        ExileEvents.GRAB_MOB_AFFIXES.callEvents(e);

                        // test
                        // var d = new ExileAffixData(LibAffixesHolder.INSTANCE.KNOCKBACK_IMMUNE.GUID(),
                        // 100);
                        // e.allAffixes.add(d);
                        // d.getAffix().getApplyStrategy().applyManually(d, en);

                        for (ExileAffixData data : e.allAffixes) {
                            data.getAffix().getApplyStrategy().onEverySecond(data, en);
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}
