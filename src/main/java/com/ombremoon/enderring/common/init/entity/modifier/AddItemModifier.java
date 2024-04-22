package com.ombremoon.enderring.common.init.entity.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(
            () -> RecordCodecBuilder.create(instance -> codecStart(instance).and(ForgeRegistries.ITEMS.getCodec()
                    .fieldOf("firstItem").forGetter(o -> o.firstItem)).and(ForgeRegistries.ITEMS.getCodec()
                    .fieldOf("secondItem").forGetter(o -> o.secondItem)).apply(instance, AddItemModifier::new)));
    private final Item firstItem;
    private final Item secondItem;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item firstItem, @Nullable Item secondItem) {
        super(conditionsIn);
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    public AddItemModifier(LootItemCondition[] conditionsIn, Item firstItem) {
        this(conditionsIn, firstItem, null);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition lootItemCondition : this.conditions) {
            if (!lootItemCondition.test(context)) {
                return generatedLoot;
            }
        }

        generatedLoot.add(new ItemStack(this.firstItem));
        if (this.secondItem != null) {
            generatedLoot.add(new ItemStack(this.secondItem));
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
