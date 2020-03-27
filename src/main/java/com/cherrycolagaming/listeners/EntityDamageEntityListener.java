package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import com.cherrycolagaming.managers.IronGolemManager;
import com.cherrycolagaming.util.MessagingUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityDamageEntityListener implements Listener {

	private final CherryColaCore plugin;
	private final IronGolemManager ironGolemManager;

	public EntityDamageEntityListener(CherryColaCore plugin, IronGolemManager ironGolemManager) {
		this.plugin = plugin;
		this.ironGolemManager = ironGolemManager;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager().getType() == EntityType.IRON_GOLEM) {
			Entity entity = e.getEntity();
			Player p = null;
			if (entity.getType() == EntityType.PLAYER) {
				p = (Player) entity;
				ironGolemManager.addWaitingToFall(p);
			}

			e.setDamage(0);

			LivingEntity damager = (LivingEntity) e.getDamager();
			if (damager.isCustomNameVisible()) {
				damager.setCustomName(MessagingUtil.format("&7Times Launched: &c" + (Integer.valueOf(ChatColor.stripColor(damager.getCustomName()).substring(16)) + 1)));
			}

			final Player player  = p;
			new BukkitRunnable() {
				@Override
				public void run() {
					Vector velocity = entity.getVelocity().multiply(15);
					if (player != null && !player.isSneaking()) {
						velocity.setX(0).setZ(0);
					}
					entity.setVelocity(velocity);
				}
			}.runTaskLaterAsynchronously(plugin, 0L);
		}
	}

}
