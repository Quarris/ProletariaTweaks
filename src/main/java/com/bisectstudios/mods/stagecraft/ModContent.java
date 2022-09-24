package com.bisectstudios.mods.stagecraft;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public class ModContent {

    public static final ITag.INamedTag<Block> ASTRAL_AUTO_LINK_TAG = BlockTags.createOptional(ModRef.res("starlight_auto_link"));

    public static void init() {}
}
