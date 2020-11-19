package org.samo_lego.golfiv;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.samo_lego.golfiv.storage.GolfConfig;

import java.io.File;

public class GolfIV implements ModInitializer {

	public static GolfConfig golfConfig;

	@Override
	public void onInitialize() {
		golfConfig = GolfConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/GolfIV_config.json"));
	}
}