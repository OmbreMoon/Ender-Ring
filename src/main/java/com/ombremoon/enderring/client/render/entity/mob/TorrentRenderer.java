package com.ombremoon.enderring.client.render.entity.mob;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.client.model.entity.mob.TorrentModel;
import com.ombremoon.enderring.common.object.entity.mob.Torrent;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TorrentRenderer extends MobRenderer<Torrent, TorrentModel<Torrent>> {
    private static ResourceLocation TEXTURE_LOCATION = CommonClass.customLocation("textures/entity/mob/torrent.png");

    public TorrentRenderer(EntityRendererProvider.Context pContext, TorrentModel<Torrent> pModel, String name) {
        super(pContext, pModel, 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(Torrent pEntity) {
        return TEXTURE_LOCATION;
    }
}
