package com.bisectstudios.mods.stagecraft.misc;

import com.bisectstudios.mods.stagecraft.mixins.WorldDataAPIAccessor;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class StageCraftWorldData extends WorldSavedData {

    public static final String KEY = "StageCraftWorldData";
    public static final WorldDataAPI DUMMY_WORLDDATAAPI = new WorldDataAPI();

    public static StageCraftWorldData get(IServerWorld world) {
        return world.getWorld().getSavedData().getOrCreate(StageCraftWorldData::new, KEY);
    }

    public StageCraftWorldData() {
        super(KEY);
    }

    @Override
    public void read(CompoundNBT nbt) {
        WorldDataAPIAccessor beWorldDataAccessor = (WorldDataAPIAccessor) DUMMY_WORLDDATAAPI;
        ListNBT beTags = nbt.getList("be_WorldDataAPI", Constants.NBT.TAG_COMPOUND);
        for (INBT inbt : beTags) {
            CompoundNBT beTag = (CompoundNBT) inbt;
            String mod = beTag.getString("Mod");
            beWorldDataAccessor.getTAGS().put(mod, beTag.getCompound("Data"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        WorldDataAPIAccessor beWorldDataAccessor = (WorldDataAPIAccessor) DUMMY_WORLDDATAAPI;
        ListNBT beTags = new ListNBT();
        beWorldDataAccessor.getTAGS().forEach((mod, tag) -> {
            CompoundNBT beTag = new CompoundNBT();
            beTag.putString("Mod", mod);
            beTag.put("Data", tag);
            beTags.add(beTag);
        });
        nbt.put("be_WorldDataAPI", beTags);
        return nbt;
    }
}
