package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import com.cherrycolagaming.util.MessagingUtil;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

	private final CherryColaCore plugin;

	public CreatureSpawnListener(CherryColaCore plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) {
			IronGolem ironGolem = (IronGolem) e.getEntity();
			ironGolem.setCustomNameVisible(true);
			ironGolem.setCustomName(MessagingUtil.format("&7Times Launched: &c0"));
			ironGolem.setPlayerCreated(false);
		}
	}

}
