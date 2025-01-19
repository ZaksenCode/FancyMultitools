package me.zaksen.fancymultitools.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class MultitoolRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final List<Ingredient> inputs;
    private final ItemStack result;

    public MultitoolRecipe(Identifier id, List<Ingredient> inputs, ItemStack result) {
        this.id = id;

        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient) {
            return false;
        }

        return inputs.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(inputs.size());
        list.addAll(inputs);
        return list;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MultitoolRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "multitool_recipe";
    }

    public static class Serializer implements RecipeSerializer<MultitoolRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "multitool_recipe";

        @Override
        public MultitoolRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> inputs = getIngredients(JsonHelper.getArray(json, "ingredients"));
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            return new MultitoolRecipe(id, inputs, result);
        }

        private DefaultedList<Ingredient> getIngredients(JsonArray array) {
            DefaultedList<Ingredient> list = DefaultedList.ofSize(array.size(), Ingredient.EMPTY);

            for(int i = 0; i < list.size(); i++) {
                list.set(i, Ingredient.fromJson(array.get(i), false));
            }

            return list;
        }

        @Override
        public MultitoolRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack result = buf.readItemStack();

            return new MultitoolRecipe(id, inputs, result);
        }

        @Override
        public void write(PacketByteBuf buf, MultitoolRecipe recipe) {
            buf.writeInt(recipe.inputs.size());

            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.result);
        }
    }
}
