package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.library_of_exile.recipe.RecipeGenerator;
import com.robertx22.mine_and_slash.aoe_data.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.mine_and_slash.aoe_data.datapacks.generators.DataGenHook;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.aoe_data.datapacks.modpack_helper_lists.ModpackerHelperLists;
import net.minecraft.data.CachedOutput;
import net.neoforged.api.distmarker.Dist;
// DistExecutor removed

public class DataGeneration {


    public static void generateAll() {

        try {
            
            // DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MMORPG::createMnsLangFile);
            // TODO: check if this is safe to run on server or if logic needs change
             MMORPG.createMnsLangFile();

            new DataGenHook().run(CachedOutput.NO_CACHE);

            ModpackerHelperLists.generate();
            GenerateCurioDataJsons.generate();
            ItemModelManager.INSTANCE.generateModels();

            RecipeGenerator.generateAll(CachedOutput.NO_CACHE, SlashRef.MODID);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
