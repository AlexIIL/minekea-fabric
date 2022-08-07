package com.chimericdream.minekea.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.minecraft.util.Identifier;

/*
 * Inspired by similar utils in the following mod:
 * https://github.com/Azagwen/ATBYW
 */
public class LootTable {
    public static Identifier getLootTableID(String blockID) {
        return getLootTableID(new Identifier(blockID));
    }

    public static Identifier getLootTableID(Identifier blockID) {
        return new Identifier(blockID.getNamespace(), "blocks/" + blockID.getPath());
    }

    public static Identifier blockID(Identifier blockID) {
        return new Identifier(blockID.getNamespace(), "blocks/" + blockID.getPath());
    }

    public static JLootTable dropSelf(Identifier blockID) {
        return JLootTable.loot("minecraft:block")
            .pool(
                JLootTable.pool()
                    .rolls(1)
                    .entry(
                        new JEntry()
                            .type("minecraft:item")
                            .name(blockID.toString())
                    )
                    .condition(new JCondition().condition("minecraft:survives_explosion"))
            );
    }

    public static JLootTable doubleBlockLootTable(Identifier blockID) {
        JsonObject lowerHalf = new JsonObject();
        lowerHalf.addProperty("half", "lower");

        return JLootTable.loot("minecraft:block")
            .pool(
                JLootTable.pool()
                    .rolls(1)
                    .entry(
                        new JEntry()
                            .type("minecraft:item")
                            .name(blockID.toString())
                            .condition(
                                new JCondition()
                                    .condition("minecraft:block_state_property")
                                    .parameter("block", blockID)
                                    .parameter("properties", lowerHalf)
                            )
                    )
                    .condition(new JCondition().condition("minecraft:survives_explosion"))
            );
    }

    public static JsonObject silkTouchPredicate() {
        var level = new JsonObject();
        level.addProperty("min", 1);

        var silkTouch = new JsonObject();
        silkTouch.addProperty("enchantment", "minecraft:silk_touch");
        silkTouch.add("levels", level);

        JsonArray enchantments = new JsonArray();
        enchantments.add(silkTouch);

        var predicate = new JsonObject();
        predicate.add("enchantments", enchantments);

        return predicate;
    }

    public static JLootTable slabLootTable(Identifier blockID) {
        JsonObject doubleType = new JsonObject();
        doubleType.addProperty("type", "double");

        return JLootTable.loot("minecraft:block")
            .pool(
                JLootTable.pool()
                    .rolls(1)
                    .entry(
                        new JEntry()
                            .type("minecraft:item")
                            .name(blockID.toString())
                            .function("minecraft:explosion_decay")
                            .function(
                                new JFunction("minecraft:set_count")
                                    .parameter("count", 2)
                                    .parameter("add", false)
                                    .condition(
                                        new JCondition()
                                            .condition("minecraft:block_state_property")
                                            .parameter("block", blockID.toString())
                                            .parameter("properties", doubleType)
                                    )
                            )
                    )
                    .condition(new JCondition().condition("minecraft:survives_explosion"))
            );
    }
}
