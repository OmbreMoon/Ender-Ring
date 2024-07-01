package com.ombremoon.enderring.common.magic;

import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;

public interface Classification extends ExtendableEnum {
    ExtendableEnumManager<Classification> ENUM_MANAGER = new ExtendableEnumManager("classification");

    MagicType getMagicType();
}
