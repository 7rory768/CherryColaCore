package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import com.cherrycolagaming.managers.IronGolemManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

	private final CherryColaCore plugin;
	private final IronGolemManager ironGolemManager;

	public EntityDamageListener(CherryColaCore plugin, IronGolemManager ironGolemManager) {
		this.plugin = plugin;
		this.ironGolemManager = ironGolemManager;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntityType() == EntityType.PLAYER && ironGolemManager.isWaitingToFall(e.getEntity())) {
			e.setCancelled(true);
			ironGolemManager.removeWaitToFall(e.getEntity());
		}
	}

}
