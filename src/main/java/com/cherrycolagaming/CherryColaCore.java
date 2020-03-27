package com.cherrycolagaming;

import com.cherrycolagaming.listeners.CreatureSpawnListener;
import com.cherrycolagaming.listeners.EntityDamageEntityListener;
import com.cherrycolagaming.listeners.EntityDamageListener;
import com.cherrycolagaming.listeners.EntityExplodeListener;
import com.cherrycolagaming.managers.IronGolemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CherryColaCore extends JavaPlugin {

	private IronGolemManager ironGolemManager;

	@Override
	public void onEnable() {
		initializeVariables();
		registerListeners();
	}

	public void initializeVariables() {
		ironGolemManager = new IronGolemManager();
	}

	public void registerListeners() {
		new CreatureSpawnListener(this);
		new EntityDamageEntityListener(this, ironGolemManager);
		new EntityDamageListener(this, ironGolemManager);
		new EntityExplodeListener(this);
	}

}
