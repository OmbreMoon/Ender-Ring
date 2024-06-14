package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.common.object.entity.npc.TestDummy;
import com.ombremoon.enderring.common.object.entity.mob.Torrent;
import com.ombremoon.enderring.common.object.entity.npc.Hewg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

public class MobInit extends EntityInit {

    public static void init() {
        TEST_DUMMY = registerMob("test_dummy", TestDummy::new, MobCategory.MISC, 0.6F, 1.95F, 4, TestDummy::createTestDummyAttributes);
    }

    public static RegistryObject<EntityType<TestDummy>> TEST_DUMMY;

    //Creatures
    public static RegistryObject<EntityType<Torrent>> TORRENT = registerMob("torrent", Torrent::new, MobCategory.CREATURE, 1.65F, 2.0F, 10, Torrent::createTorrentAttributes, false);

    //NPCs
    public static RegistryObject<EntityType<Hewg>> HEWG = registerMob("hewg", Hewg::new, MobCategory.MISC, 0.6F, 1.95F, 4, Hewg::createHewgAttributes);

    //Hostile Mobs
    //Soldier of Godrick
    //Margit
    //Bloodhound Knight
    //
}
