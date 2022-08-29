package com.bisectstudios.mods.proletaria.configs;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import wayoftime.bloodmagic.altar.ComponentType;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;

import java.util.ArrayList;
import java.util.List;

public class ModConfigs {

    public static ForgeConfigSpec.ConfigValue<String> oceanMonumentTrophyReplacement;

    // Bloodmagic
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> glowstoneCaps;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> bloodstoneCaps;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> beacons;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> crystals;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> bloodrunes;

    public static ForgeConfigSpec register(ForgeConfigSpec.Builder builder) {
        builder.push("ocean_monument").comment("Ocean Monument Tweaks");
        oceanMonumentTrophyReplacement = builder.define("trophy_room", "minecraft:gold_block");
        builder.pop();

        builder.push("bloodmagic");
        glowstoneCaps = builder.comment("Glowstone Cap Blocks")
            .defineListAllowEmpty(Lists.newArrayList("glowstone"),
                () -> getDefaultComponentList(ComponentType.GLOWSTONE), ModConfigs::isResourceLocation
            );

        bloodstoneCaps = builder.comment("Bloodstone Cap Blocks")
            .defineListAllowEmpty(Lists.newArrayList("bloodstone"),
                () -> getDefaultComponentList(ComponentType.BLOODSTONE), ModConfigs::isResourceLocation
            );

        beacons = builder.comment("Beacon Cap Blocks")
            .defineListAllowEmpty(Lists.newArrayList("beacons"),
                () -> getDefaultComponentList(ComponentType.BEACON), ModConfigs::isResourceLocation
            );

        crystals = builder.comment("Crystal Cap Blocks")
            .defineListAllowEmpty(Lists.newArrayList("crystal"),
                () -> getDefaultComponentList(ComponentType.CRYSTAL), ModConfigs::isResourceLocation
            );

        bloodrunes = builder.comment("Bloodrune Blocks")
            .defineListAllowEmpty(Lists.newArrayList("bloodrune"),
                () -> getDefaultComponentList(ComponentType.BLOODRUNE), ModConfigs::isResourceLocation
            );

        builder.pop();
        return builder.build();
    }

    private static List<String> getDefaultComponentList(ComponentType type) {
        List<String> list = new ArrayList<>();

        switch (type) {
            case GLOWSTONE:
                list.add(Blocks.GLOWSTONE.getRegistryName().toString());
                list.add(Blocks.SEA_LANTERN.getRegistryName().toString());
                break;
            case BLOODSTONE:
                list.add(BloodMagicBlocks.BLOODSTONE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.BLOODSTONE_BRICK.get().getRegistryName().toString());
                break;
            case BEACON:
                list.add(Blocks.BEACON.getRegistryName().toString());
                break;
            case BLOODRUNE:
                list.add(BloodMagicBlocks.BLANK_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.BLANK_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.SPEED_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.SACRIFICE_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.SELF_SACRIFICE_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.DISPLACEMENT_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.CAPACITY_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.AUGMENTED_CAPACITY_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.ORB_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.ACCELERATION_RUNE.get().getRegistryName().toString());
                list.add(BloodMagicBlocks.CHARGING_RUNE.get().getRegistryName().toString());
                break;
            case CRYSTAL:
            default:
        }

        return list;
    }

    private static boolean isResourceLocation(Object obj) {
        if (!(obj instanceof String)) {
            return false;
        }
        try {
            new ResourceLocation((String) obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
