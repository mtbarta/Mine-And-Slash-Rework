package com.robertx22.mine_and_slash.event_hooks.my_events;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class EntityLevelUpEvent extends Event {
    private final LivingEntity entity;
    private final int newLevel;

    public EntityLevelUpEvent(LivingEntity entity, int newLevel) {
        this.entity = entity;
        this.newLevel = newLevel;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public int getNewLevel() {
        return newLevel;
    }
}
