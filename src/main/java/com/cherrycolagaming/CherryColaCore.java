package com.cherrycolagaming;

import com.cherrycolagaming.listeners.*;
import com.cherrycolagaming.managers.IronGolemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CherryColaCore extends JavaPlugin {

	private static CherryColaCore instance;

	private IronGolemManager ironGolemManager;

	@Override
	public void onEnable() {
		instance = this;
		initializeVariables();
		registerListeners();
	}

	public static CherryColaCore getInstance() {
		return instance;
	}

	public void initializeVariables() {
		ironGolemManager = new IronGolemManager();
	}

	public void registerListeners() {
		new BeeHiveListener(this);
		new CreatureSpawnListener(this);
		new EntityDamageEntityListener(this, ironGolemManager);
		new EntityDamageListener(this, ironGolemManager);
		new EntityExplodeListener(this);
		new ShearSheepListener(this);
	}

}
