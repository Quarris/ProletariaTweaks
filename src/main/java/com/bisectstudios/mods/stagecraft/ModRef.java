package com.bisectstudios.mods.stagecraft;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRef {

    public static final String ID = "stagecraft";
    public static final Logger LOGGER = LogManager.getLogger("StageCraft Tweaks");

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }

}
