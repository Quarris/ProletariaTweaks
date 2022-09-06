package com.bisectstudios.mods.proletaria;

import com.bisectstudios.mods.proletaria.compat.BloodMagicCompat;
import com.bisectstudios.mods.proletaria.configs.ModConfigs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModRef.ID)
public class ModRoot {

    public ModRoot() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.register(new ForgeConfigSpec.Builder()));

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.LOWEST, this::onLoadCompleted);

        ModContent.init();
    }

    private void onLoadCompleted(FMLLoadCompleteEvent event) {
        BloodMagicCompat.onLoadCompleted();
    }
}
