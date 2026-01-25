package com.robertx22.mns_cobblemon.mixin;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Pokemon.class)
public abstract class PokemonMixin {

    /**
     * Shadow the existing method which takes a boolean argument.
     */
    @Shadow
    public abstract MutableComponent getDisplayName(boolean showTitle);

    /**
     * Add the missing no-arg method and forward to the existing one.
     */
    public MutableComponent getDisplayName() {
        return this.getDisplayName(false);
    }
}
