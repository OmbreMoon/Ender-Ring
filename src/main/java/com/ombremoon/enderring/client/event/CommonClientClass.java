package com.ombremoon.enderring.client.event;

import com.ombremoon.enderring.client.ModelLocations;
import com.ombremoon.enderring.client.model.MidasShoulderGuardTwoModel;
import com.ombremoon.enderring.client.model.entity.TestDummyModel;
import com.ombremoon.enderring.client.model.entity.mob.SpiritJellyfishModel;
import com.ombremoon.enderring.client.model.entity.mob.TorrentModel;
import com.ombremoon.enderring.client.model.entity.projectile.spell.GlintbladePhalanxModel;
import com.ombremoon.enderring.client.model.entity.projectile.spell.GlintstoneArcModel;
import com.ombremoon.enderring.client.render.entity.TestDummyRenderer;
import com.ombremoon.enderring.client.render.entity.mob.SpiritJellyfishRenderer;
import com.ombremoon.enderring.client.render.entity.mob.TorrentRenderer;
import com.ombremoon.enderring.client.render.entity.projectile.ThrowingPotRenderer;
import com.ombremoon.enderring.client.render.entity.projectile.spell.GlintbladePhalanxRenderer;
import com.ombremoon.enderring.client.render.entity.projectile.spell.GlintstoneArcRenderer;
import com.ombremoon.enderring.common.init.blocks.BlockEntityInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CommonClientClass {
    public static <T extends Entity> List<Renderers> getRenderers() {
        return List.of(
                //MOBS
                new Renderers(MobInit.TEST_DUMMY, (context) -> new TestDummyRenderer(context, new TestDummyModel<>(context.bakeLayer(ModelLocations.TEST_DUMMY)))),
                new Renderers(MobInit.TORRENT, (context) -> new TorrentRenderer(context, new TorrentModel<>(context.bakeLayer(ModelLocations.TORRENT)))),
                new Renderers(MobInit.SPIRIT_JELLYFISH, (context) -> new SpiritJellyfishRenderer(context, new SpiritJellyfishModel<>(context.bakeLayer(ModelLocations.SPIRIT_JELLYFISH)))),

                //PROJECTILES
                new Renderers(ProjectileInit.THROWING_POT, ThrowingPotRenderer::new),

                //SPELL PROJECTILES
                new Renderers(ProjectileInit.GLINTSTONE_ARC, GlintstoneArcRenderer::new),
                new Renderers(ProjectileInit.GLINTSTONE_PHALANX, GlintbladePhalanxRenderer::new)

        );
    }

    public static List<LayerDefinitions> getLayerDefinitions() {
        List<LayerDefinitions> definitions = new ArrayList<>();
        definitions.addAll(List.of(
                new LayerDefinitions(ModelLocations.TEST_DUMMY, TestDummyModel.createBodyLayer()),
                new LayerDefinitions(ModelLocations.TORRENT, TorrentModel.createBodyLayer()),
                new LayerDefinitions(ModelLocations.SPIRIT_JELLYFISH, SpiritJellyfishModel.createBodyLayer()),
                new LayerDefinitions(ModelLocations.GLINTSTONE_ARC, GlintstoneArcModel.createBodyLayer()),
                new LayerDefinitions(ModelLocations.GLINTBLADE_PHALANX, GlintbladePhalanxModel.createBodyLayer()),
                new LayerDefinitions(ModelLocations.MIDAS_SHOULDER_GUARD, MidasShoulderGuardTwoModel.createBodyLayer())
        ));
        return definitions;
    }

    public record Renderers<T extends Entity>(Supplier<EntityType<T>> type, EntityRendererProvider<T> renderer) {
    }

    public record LayerDefinitions(ModelLayerLocation layerLocation, LayerDefinition supplier) {
    }
}
