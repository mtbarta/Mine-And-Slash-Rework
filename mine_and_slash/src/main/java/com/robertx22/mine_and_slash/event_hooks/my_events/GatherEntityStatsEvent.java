package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

import java.util.List;

/**
 * Fired during stat calculation to allow external mods to add additional
 * StatContexts.
 * This is useful for compatibility mods that want to inject stats based on
 * external data.
 */
public class GatherEntityStatsEvent extends Event {
    private final LivingEntity entity;
    private final List<StatContext> statContexts;

    public GatherEntityStatsEvent(LivingEntity entity, List<StatContext> statContexts) {
        this.entity = entity;
        this.statContexts = statContexts;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public List<StatContext> getStatContexts() {
        return statContexts;
    }

    public void addStatContext(StatContext context) {
        this.statContexts.add(context);
    }
}
