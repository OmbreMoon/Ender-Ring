package com.ombremoon.enderring.common.init.blocks;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.block.entity.GraceSiteBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<GraceSiteBlockEntity>> GRACE_SITE = BLOCK_ENTITY_TYPES.register("grace_site", () -> BlockEntityType.Builder.of(GraceSiteBlockEntity::new, BlockInit.GRACE_SITE.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
