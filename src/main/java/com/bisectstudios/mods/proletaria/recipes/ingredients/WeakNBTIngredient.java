package com.bisectstudios.mods.proletaria.recipes.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class WeakNBTIngredient extends Ingredient {

    private final ItemStack stack;

    protected WeakNBTIngredient(ItemStack stack) {
        super(Stream.of(new Ingredient.SingleItemList(stack)));
        this.stack = stack;
    }

    @Override
    public boolean test(@Nullable ItemStack input)
    {
        if (input == null)
            return false;
        //Can't use areItemStacksEqualUsingNBTShareTag because it compares stack size as well
        return this.stack.getItem() == input.getItem() &&
            this.stack.getDamage() == input.getDamage() &&
            areNbtCompatible(this.stack.getShareTag(), input.getShareTag());
    }

    private static boolean areNbtCompatible(CompoundNBT match, CompoundNBT input) {
        if (match == null || match.isEmpty()) {
            return true;
        }

        if (input == null || input.isEmpty()) {
            return false;
        }

        for (String baseNbtKey : match.keySet()) {
            if (!input.contains(baseNbtKey)) {
                return false;
            }

            INBT baseMatch = match.get(baseNbtKey);
            INBT baseInput = input.get(baseNbtKey);

            if (baseMatch instanceof CompoundNBT) {
                if (!(baseInput instanceof CompoundNBT)) {
                    return false;
                }

                if (!areNbtCompatible((CompoundNBT) baseMatch, (CompoundNBT) baseInput)) {
                    return false;
                }

                continue;
            }

            if (!Objects.equals(baseMatch, baseInput)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isSimple()
    {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement serialize()
    {
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        json.addProperty("count", stack.getCount());
        if (stack.hasTag())
            json.addProperty("nbt", stack.getTag().toString());
        return json;
    }

    public static class Serializer implements IIngredientSerializer<WeakNBTIngredient>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public WeakNBTIngredient parse(PacketBuffer buffer) {
            return new WeakNBTIngredient(buffer.readItemStack());
        }

        @Override
        public WeakNBTIngredient parse(JsonObject json) {
            return new WeakNBTIngredient(CraftingHelper.getItemStack(json, true));
        }

        @Override
        public void write(PacketBuffer buffer, WeakNBTIngredient ingredient) {
            buffer.writeItemStack(ingredient.stack);
        }
    }
}
