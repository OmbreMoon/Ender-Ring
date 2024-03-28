package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.common.object.item.magic.SealItem;
import com.ombremoon.enderring.common.object.item.magic.StaffItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class EquipmentInit extends ItemInit {
    public static void init() {}

    //Staves & Seals
    public static RegistryObject<Item> ASTROLOGER_STAFF = registerStaffItem("astrologer_staff");
    public static RegistryObject<Item> GLINTSTONE_STAFF = registerStaffItem("glintstone_staff");
    public static RegistryObject<Item> FINGER_SEAL = registerSealItem("finger_seal");

    public static RegistryObject<Item> registerStaffItem(String name) {
        RegistryObject<Item> registryObject = registerItem(name, () -> new StaffItem(getItemProperties()));
        EQUIPMENT_LIST.add(registryObject);
        return registryObject;
    }

    public static RegistryObject<Item> registerSealItem(String name) {
        RegistryObject<Item> registryObject = registerItem(name, () -> new SealItem(getItemProperties()));
        EQUIPMENT_LIST.add(registryObject);
        return registryObject;
    }
}
