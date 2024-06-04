package com.ombremoon.enderring;

import net.tslat.smartbrainlib.SBLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class Constants {
    public static final String MOD_ID = "enderring";
    public static final String MOD_NAME = "Ender Ring";
    public static final String ABBR_NAME = "ER";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final SBLLoader SBL_LOADER = ServiceLoader.load(SBLLoader.class).findFirst().get();
}
