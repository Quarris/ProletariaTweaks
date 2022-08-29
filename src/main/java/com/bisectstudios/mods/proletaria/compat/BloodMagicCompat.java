package com.bisectstudios.mods.proletaria.compat;

import com.bisectstudios.mods.proletaria.configs.ModConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import wayoftime.bloodmagic.altar.ComponentType;
import wayoftime.bloodmagic.api.IBloodMagicAPI;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.List;

public class BloodMagicCompat {

    public static void onLoadCompleted() {
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(Blocks.GLOWSTONE.getDefaultState(), ComponentType.GLOWSTONE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(Blocks.SEA_LANTERN.getDefaultState(), ComponentType.GLOWSTONE.name());

        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.BLOODSTONE.get().getDefaultState(), ComponentType.BLOODSTONE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent((BloodMagicBlocks.BLOODSTONE_BRICK.get()).getDefaultState(), ComponentType.BLOODSTONE.name());

        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(Blocks.BEACON.getDefaultState(), ComponentType.BEACON.name());

        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.BLANK_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.SPEED_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.SACRIFICE_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.SELF_SACRIFICE_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.DISPLACEMENT_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.CAPACITY_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.ORB_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.ACCELERATION_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.CHARGING_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());

        registerAltarCaps(ComponentType.GLOWSTONE, ModConfigs.glowstoneCaps.get());
        registerAltarCaps(ComponentType.BLOODSTONE, ModConfigs.bloodstoneCaps.get());
        registerAltarCaps(ComponentType.BLOODRUNE, ModConfigs.bloodrunes.get());
        registerAltarCaps(ComponentType.BEACON, ModConfigs.beacons.get());
        registerAltarCaps(ComponentType.CRYSTAL, ModConfigs.crystals.get());
    }

    // If ever need to parse block states. Look at SetBlockCommand and BlockStateParser
    private static void registerAltarCaps(ComponentType type, List<? extends String> caps) {
        caps.stream().map(ResourceLocation::new).forEach(res -> {
            Block block = ForgeRegistries.BLOCKS.getValue(res);
            if (block.getStateContainer().getProperties().isEmpty()) {
                IBloodMagicAPI.INSTANCE.getValue().registerAltarComponent(block.getDefaultState(), type.name());
            } else {
                block.getStateContainer().getProperties().forEach(prop ->
                    prop.getAllowedValues().forEach(value ->
                        IBloodMagicAPI.INSTANCE.getValue().registerAltarComponent(block.getDefaultState().<Comparable, Comparable>with((Property) prop, value), type.name())
                    )
                );
            }
        });
    }
}
