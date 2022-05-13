package com.chimericdream.minekea.block.decorations.candles;

import com.chimericdream.minekea.ModInfo;
import com.chimericdream.minekea.resource.LootTable;
import com.chimericdream.minekea.resource.MinekeaResourcePack;
import com.chimericdream.minekea.resource.Model;
import com.chimericdream.minekea.util.MinekeaBlock;
import com.google.gson.JsonObject;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.CandleBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class GenericVotiveCandle extends CandleBlock implements MinekeaBlock {
    private final Identifier BLOCK_ID;

    public GenericVotiveCandle() {
        super(FabricBlockSettings.copyOf(Blocks.BLUE_CANDLE));

        BLOCK_ID = new Identifier(ModInfo.MOD_ID, "decorations/candles/blue_votive_candle");
    }

    @Override
    public Identifier getBlockID() {
        return BLOCK_ID;
    }

    @Override
    public void register() {
        Registry.register(Registry.BLOCK, BLOCK_ID, this);
        Registry.register(Registry.ITEM, BLOCK_ID, new BlockItem(this, new Item.Settings().group(ItemGroup.DECORATIONS)));

        setupResources();
    }

    @Override
    public void setupResources() {
        MinekeaResourcePack.RESOURCE_PACK.addRecipe(
            BLOCK_ID,
            JRecipe.shapeless(
                JIngredients.ingredients()
                    .add(JIngredient.ingredient().item("minecraft:blue_candle"))
                    .add(JIngredient.ingredient().item("minecraft:blue_candle"))
                    .add(JIngredient.ingredient().item("minecraft:blue_candle"))
                    .add(JIngredient.ingredient().item("minecraft:blue_candle"))
                    .add(JIngredient.ingredient().item("minecraft:glass")),
                JResult.stackedResult(BLOCK_ID.toString(), 4)
            )
        );

        // Loot Table
        JsonObject twoCandles = new JsonObject();
        twoCandles.addProperty("candles", "2");

        JsonObject threeCandles = new JsonObject();
        threeCandles.addProperty("candles", "3");

        JsonObject fourCandles = new JsonObject();
        fourCandles.addProperty("candles", "4");

        MinekeaResourcePack.RESOURCE_PACK.addLootTable(
            LootTable.blockID(BLOCK_ID),
            JLootTable.loot("minecraft:block")
                .pool(
                    JLootTable.pool()
                        .rolls(1)
                        .entry(new JEntry().type("minecraft:item").name(BLOCK_ID.toString()))
                        .function(
                            new JFunction("minecraft:set_count")
                                .parameter("count", 2)
                                .parameter("add", false)
                                .condition(
                                    new JCondition()
                                        .condition("minecraft:block_state_property")
                                        .parameter("block", BLOCK_ID.toString())
                                        .parameter("properties", twoCandles)
                                )
                        )
                        .function(
                            new JFunction("minecraft:set_count")
                                .parameter("count", 3)
                                .parameter("add", false)
                                .condition(
                                    new JCondition()
                                        .condition("minecraft:block_state_property")
                                        .parameter("block", BLOCK_ID.toString())
                                        .parameter("properties", threeCandles)
                                )
                        )
                        .function(
                            new JFunction("minecraft:set_count")
                                .parameter("count", 4)
                                .parameter("add", false)
                                .condition(
                                    new JCondition()
                                        .condition("minecraft:block_state_property")
                                        .parameter("block", BLOCK_ID.toString())
                                        .parameter("properties", fourCandles)
                                )
                        )
                        .function(new JFunction("minecraft:explosion_decay"))
                )

        );
        /*
         * {
         *     "type": "minecraft:block",
         *     "pools": [
         *         {
         *             "rolls": 1.0,
         *             "bonus_rolls": 0.0,
         *             "entries": [
         *                 {
         *                     "type": "minecraft:item",
         *                     "functions": [
         *                         {
         *                             "function": "minecraft:set_count",
         *                             "conditions": [
         *                                 {
         *                                     "condition": "minecraft:block_state_property",
         *                                     "block": "minecraft:blue_candle",
         *                                     "properties": {"candles": "3"}
         *                                 }
         *                             ],
         *                             "count": 3.0,
         *                             "add": false
         *                         },
         *                         {
         *                             "function": "minecraft:set_count",
         *                             "conditions": [
         *                                 {
         *                                     "condition": "minecraft:block_state_property",
         *                                     "block": "minecraft:blue_candle",
         *                                     "properties": {"candles": "4"}
         *                                 }
         *                             ],
         *                             "count": 4.0,
         *                             "add": false
         *                         },
         *                         {"function": "minecraft:explosion_decay"}
         *                     ],
         *                     "name": "minecraft:blue_candle"
         *                 }
         *             ]
         *         }
         *     ]
         * }
         */

        // Models
        Identifier BASE_MODEL_ID = Model.getBlockModelID(BLOCK_ID);
        Identifier ITEM_MODEL_ID = Model.getItemModelID(BLOCK_ID);

        Map<String, Identifier> MODEL_IDS = new HashMap<>();
        MODEL_IDS.put("one", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_one"));
        MODEL_IDS.put("one_lit", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_one_lit"));
        MODEL_IDS.put("two", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_two"));
        MODEL_IDS.put("two_lit", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_two_lit"));
        MODEL_IDS.put("three", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_three"));
        MODEL_IDS.put("three_lit", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_three_lit"));
        MODEL_IDS.put("four", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_four"));
        MODEL_IDS.put("four_lit", new Identifier(BASE_MODEL_ID.getNamespace(), BASE_MODEL_ID.getPath() + "_four_lit"));

        MinekeaResourcePack.RESOURCE_PACK.addModel(JModel.model(MODEL_IDS.get("one")), ITEM_MODEL_ID);

        JTextures unlit = new JTextures()
            .var("all", "minecraft:block/blue_candle")
            .var("particle", "minecraft:block/blue_candle");

        JTextures lit = new JTextures()
            .var("all", "minecraft:block/blue_candle_lit")
            .var("particle", "minecraft:block/blue_candle_lit");

        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_votive_candle").textures(unlit),
            MODEL_IDS.get("one")
        );
        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_votive_candle").textures(lit),
            MODEL_IDS.get("one_lit")
        );

        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_two_votive_candles").textures(unlit),
            MODEL_IDS.get("two")
        );
        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_two_votive_candles").textures(lit),
            MODEL_IDS.get("two_lit")
        );

        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_three_votive_candles").textures(unlit),
            MODEL_IDS.get("three")
        );
        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_three_votive_candles").textures(lit),
            MODEL_IDS.get("three_lit")
        );

        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_four_votive_candles").textures(unlit),
            MODEL_IDS.get("four")
        );
        MinekeaResourcePack.RESOURCE_PACK.addModel(
            JModel.model("minekea:block/candles/template_four_votive_candles").textures(lit),
            MODEL_IDS.get("four_lit")
        );

        /*
         * {
         *     "parent": "minekea:block/candles/template_votive_candle",
         *     "textures": {"all": "minecraft:block/blue_candle", "particle": "minecraft:block/blue_candle"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_votive_candle",
         *     "textures": {"all": "minecraft:block/blue_candle_lit", "particle": "minecraft:block/blue_candle_lit"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_two_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle", "particle": "minecraft:block/blue_candle"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_two_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle_lit", "particle": "minecraft:block/blue_candle_lit"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_three_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle", "particle": "minecraft:block/blue_candle"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_three_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle_lit", "particle": "minecraft:block/blue_candle_lit"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_four_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle", "particle": "minecraft:block/blue_candle"}
         * }
         * {
         *     "parent": "minekea:block/candles/template_four_votive_candles",
         *     "textures": {"all": "minecraft:block/blue_candle_lit", "particle": "minecraft:block/blue_candle_lit"}
         * }
         */

        // Blockstate
        MinekeaResourcePack.RESOURCE_PACK.addBlockState(
            JState.state(
                JState.variant()
                    .put("candles=1,lit=false", new JBlockModel(MODEL_IDS.get("one")))
                    .put("candles=1,lit=true", new JBlockModel(MODEL_IDS.get("one_lit")))
                    .put("candles=2,lit=false", new JBlockModel(MODEL_IDS.get("two")))
                    .put("candles=2,lit=true", new JBlockModel(MODEL_IDS.get("two_lit")))
                    .put("candles=3,lit=false", new JBlockModel(MODEL_IDS.get("three")))
                    .put("candles=3,lit=true", new JBlockModel(MODEL_IDS.get("three_lit")))
                    .put("candles=4,lit=false", new JBlockModel(MODEL_IDS.get("four")))
                    .put("candles=4,lit=true", new JBlockModel(MODEL_IDS.get("four_lit")))
            ),
            BLOCK_ID
        );

        /*
         * {
         *     "variants": {
         *         "candles=1,lit=false": {"model": "minekea:block/candles/blue_votive_one_candle"},
         *         "candles=1,lit=true": {"model": "minekea:block/candles/blue_votive_one_candle_lit"},
         *         "candles=2,lit=false": {"model": "minekea:block/candles/blue_votive_two_candles"},
         *         "candles=2,lit=true": {"model": "minekea:block/candles/blue_votive_two_candles_lit"},
         *         "candles=3,lit=false": {"model": "minekea:block/candles/blue_votive_three_candles"},
         *         "candles=3,lit=true": {"model": "minekea:block/candles/blue_votive_three_candles_lit"},
         *         "candles=4,lit=false": {"model": "minekea:block/candles/blue_votive_four_candles"},
         *         "candles=4,lit=true": {"model": "minekea:block/candles/blue_votive_four_candles_lit"}
         *     }
         * }
         */
    }

    @Override
    public void validateMaterials(Map<String, Identifier> materials) {
        MinekeaBlock.super.validateMaterials(materials);
    }
}
