package com.silvergaming.LifeSafer;

import org.bukkit.plugin.java.JavaPlugin;

public class LifeSafer extends JavaPlugin {
	
	public void onEnable() {
		LifeListener lf = new LifeListener(this);		
		getServer().getPluginManager().registerEvents(lf, this);
		
	}

}
