package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.client.render.MidasGauntletRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Consumer;

public class MidasGauntletItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public MidasGauntletItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private MidasGauntletRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new MidasGauntletRenderer();
                }

                return this.renderer;
            }
        });
    }
}
