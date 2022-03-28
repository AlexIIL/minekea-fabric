package com.chimericdream.minekea.tag;

import com.chimericdream.minekea.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MinekeaTags {
    public static final TagKey<Block> BOOKSHELVES = TagKey.of(Registry.BLOCK_KEY, new Identifier(ModInfo.MOD_ID, "bookshelves"));
    public static final TagKey<Block> BOOKSHELVES_WITH_STORAGE = TagKey.of(Registry.BLOCK_KEY, new Identifier(ModInfo.MOD_ID, "bookshelves_with_storage"));
    public static final TagKey<Block> CRATES = TagKey.of(Registry.BLOCK_KEY, new Identifier(ModInfo.MOD_ID, "crates"));

    public static void init() {

    }
}