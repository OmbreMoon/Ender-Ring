package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.common.object.entity.mob.Torrent;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//TODO: ADD SOUND

public class TorrentWhistleItem extends Item implements IQuickAccess {
    public TorrentWhistleItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            Torrent torrent = new Torrent(pLevel, pPlayer);
            if (this.canSpawnTorrent(pPlayer, pLevel)) {
                torrent.setOwnerUUID(pPlayer.getUUID());
                torrent.setHealth((float) PlayerStatusUtil.getTorrentHealth(pPlayer));
                pLevel.addFreshEntity(torrent);
                pPlayer.startRiding(torrent, true);
                PlayerStatusUtil.setTorrentSpawned(pPlayer, true);

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
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
