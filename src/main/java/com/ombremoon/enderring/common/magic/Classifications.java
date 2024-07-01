package com.ombremoon.enderring.common.magic;

public enum Classifications implements Classification {
    ABERRANT(MagicType.SORCERY),
    BESTIAL(MagicType.INCANTATION),
    BLOOD(MagicType.INCANTATION),
    CARIAN(MagicType.SORCERY),
    CLAYMEN(MagicType.SORCERY),
    COLD(MagicType.SORCERY),
    CRYSTALIAN(MagicType.SORCERY),
    DEATH(MagicType.SORCERY),
    DRAGON_COMMUNION(MagicType.INCANTATION),
    DRAGON_CULT(MagicType.INCANTATION),
    ERDTREE(MagicType.INCANTATION),
    FIRE_GIANT(MagicType.INCANTATION),
    FIRE_MONK(MagicType.INCANTATION),
    FRENZIED(MagicType.INCANTATION),
    FULL_MOON(MagicType.SORCERY),
    GLINTSTONE(MagicType.SORCERY),
    GODSKIN(MagicType.INCANTATION),
    GOLDEN_ORDER(MagicType.INCANTATION),
    GRAVITY(MagicType.SORCERY),
    LORETTA(MagicType.SORCERY),
    MAGMA(MagicType.INCANTATION),
    NIGHT(MagicType.SORCERY),
    PRIMEVAL(MagicType.SORCERY),
    SERVANT_OF_ROT(MagicType.INCANTATION),
    TWO_FINGERS(MagicType.INCANTATION);

    final MagicType magicType;
    final int id;

    Classifications(MagicType magicType) {
        this.id = Classification.ENUM_MANAGER.assign(this);
        this.magicType = magicType;
    }

    @Override
    public MagicType getMagicType() {
        return this.magicType;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
