package com.robertx22.mine_and_slash.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.AttributeStat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeStatSer implements IStatSerializer<AttributeStat> {

    @Override
    public JsonObject statToJson(AttributeStat obj) {
        JsonObject json = new JsonObject();
        json.addProperty("attribute_id", obj.attributeId);
        // In NeoForge 1.21, modifierId is ResourceLocation
        json.addProperty("modifier_id", obj.modifierId.toString());
        json.addProperty("operation", obj.operation.name());
        json.addProperty("cut_by_hundred", obj.cut_by_hundred);

        this.saveBaseStatValues(obj, json);
        return json;
    }

    @Override
    public AttributeStat getStatFromJson(JsonObject json) {

        ResourceLocation ide = ResourceLocation.parse(json.get("attribute_id").getAsString());

        var attri = BuiltInRegistries.ATTRIBUTE.getHolder(ide).orElseThrow();

        var oper = AttributeModifier.Operation.valueOf(json.get("operation").getAsString());

        boolean cut = json.get("cut_by_hundred").getAsBoolean();

        // In NeoForge 1.21, use ResourceLocation for modifier ID
        // Support both old "uuid" format and new "modifier_id" format for backwards
        // compatibility
        ResourceLocation modifierId;
        if (json.has("modifier_id")) {
            modifierId = ResourceLocation.parse(json.get("modifier_id").getAsString());
        } else if (json.has("uuid")) {
            // Convert old UUID format to a ResourceLocation
            modifierId = ResourceLocation.fromNamespaceAndPath("mns",
                    "legacy_" + json.get("uuid").getAsString().replace("-", "_"));
        } else {
            modifierId = ResourceLocation.fromNamespaceAndPath("mns", "unknown");
        }

        AttributeStat stat = new AttributeStat("", "", modifierId, attri, false,
                oper, cut); // percent and id is loaded by basevalues

        this.loadBaseStatValues(stat, json);
        return stat;
    }
}