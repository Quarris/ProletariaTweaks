package com.bisectstudios.mods.proletaria.compat;

import com.bisectstudios.mods.proletaria.ModRef;
import com.bisectstudios.mods.proletaria.configs.ModConfigs;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import wayoftime.bloodmagic.altar.ComponentType;
import wayoftime.bloodmagic.api.IBloodMagicAPI;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BloodMagicCompat {

    public static void onLoadCompleted() {
        /*IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(Blocks.GLOWSTONE.getDefaultState(), ComponentType.GLOWSTONE.name());
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
        IBloodMagicAPI.INSTANCE.getValue().unregisterAltarComponent(BloodMagicBlocks.CHARGING_RUNE.get().getDefaultState(), ComponentType.BLOODRUNE.name());*/

        registerAltarCaps(ComponentType.GLOWSTONE, ModConfigs.glowstoneCaps.get());
        registerAltarCaps(ComponentType.BLOODSTONE, ModConfigs.bloodstoneCaps.get());
        registerAltarCaps(ComponentType.BLOODRUNE, ModConfigs.bloodrunes.get());
        registerAltarCaps(ComponentType.BEACON, ModConfigs.beacons.get());
        registerAltarCaps(ComponentType.CRYSTAL, ModConfigs.crystals.get());
    }

    // If ever need to parse block states. Look at SetBlockCommand and BlockStateParser
    private static void registerAltarCaps(ComponentType type, List<? extends String> configBlocks) {
        configBlocks.stream().forEach(stringBlock -> {
            try {
                BlockStateParser parser = new BlockStateParser(new StringReader(stringBlock), true).parse(false);
                if (parser.getProperties().isEmpty()) {
                    IBloodMagicAPI.INSTANCE.getValue().registerAltarComponent(parser.getState(), type.name());
                } else {
                    List<BlockState> validStates = parser.getState().getBlock().getStateContainer().getValidStates().stream()
                        .filter(
                            state -> {
                                for (Map.Entry<Property<?>, Comparable<?>> entry : parser.getProperties().entrySet()) {
                                    if (state.get(entry.getKey()) != entry.getValue()) {
                                        return false;
                                    }
                                }

                                return true;
                            }
                        ).collect(Collectors.toList());

                    validStates.forEach(state ->
                        IBloodMagicAPI.INSTANCE.getValue().registerAltarComponent(state, type.name())
                    );
                }
            } catch (CommandSyntaxException e) {
                ModRef.LOGGER.warn("Could not parse block {}", stringBlock);
            }
        });
    }
}
