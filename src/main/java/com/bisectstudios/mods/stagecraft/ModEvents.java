package com.bisectstudios.mods.stagecraft;

import com.bisectstudios.mods.stagecraft.misc.StageCraftWorldData;
import com.bisectstudios.mods.stagecraft.mixins.WorldDataAPIAccessor;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(modid = ModRef.ID)
public class ModEvents {

    @SubscribeEvent
    public static void loadPortalPosition(WorldEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        StageCraftWorldData.get(((ServerWorld) event.getWorld()));
    }

    @SubscribeEvent
    public static void clearBETagsOnServerStart(FMLServerAboutToStartEvent event) {
        ((WorldDataAPIAccessor) StageCraftWorldData.DUMMY_WORLDDATAAPI).getTAGS().clear();
    }
}
