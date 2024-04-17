package com.ombremoon.enderring.client;

import com.ombremoon.enderring.client.model.entity.mob.TorrentModel;
import com.ombremoon.enderring.client.render.entity.mob.TorrentRenderer;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
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
                new Renderers(MobInit.TORRENT, (context) -> new TorrentRenderer(context, new TorrentModel<>(context.bakeLayer(TorrentModel.LAYER_LOCATION)), "torrent"))
        );
    }

    public static List<LayerDefinitions> getLayerDefinitions() {
        List<LayerDefinitions> definitions = new ArrayList<>();
        definitions.addAll(List.of(
                new LayerDefinitions(TorrentModel.LAYER_LOCATION, TorrentModel.createBodyLayer())
        ));
        return definitions;
    }

    public record Renderers<T extends Entity>(Supplier<EntityType<T>> type, EntityRendererProvider<T> renderer) {
    }

    public record LayerDefinitions(ModelLayerLocation layerLocation, LayerDefinition supplier) {
    }
}
