package com.bisectstudios.mods.proletaria;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRef {

    public static final String ID = "proletaria";
    public static final Logger LOGGER = LogManager.getLogger("ProletariaTweaks");

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }

}
