package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.common.object.entity.mob.Torrent;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//TODO: ADD SOUND

public class TorrentWhistleItem extends QuickAccessItem {
    public TorrentWhistleItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public void useItem(Player player, Level level, InteractionHand usedHand, ItemStack itemStack) {
        if (!level.isClientSide) {
            Torrent torrent = new Torrent(level, player);
            if (this.canSpawnTorrent(player, level)) {
                torrent.setOwnerUUID(player.getUUID());
                torrent.setHealth((float) PlayerStatusUtil.getTorrentHealth(player));
                level.addFreshEntity(torrent);
                player.startRiding(torrent, true);
                PlayerStatusUtil.setTorrentSpawned(player, true);
            }
        }
    }

    private boolean canSpawnTorrent(Player player, Level level) {
        if (player.isSpectator()) {
            return false;
        } else if (PlayerStatusUtil.isTorrentSpawnedOrIncapacitated(player)) {
            return false;
        } else {
            return !player.isInWall() && !player.isFallFlying() && player.getVehicle() == null;
        }
    }
}
