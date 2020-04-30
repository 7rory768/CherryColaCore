package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class BeeHiveListener implements Listener {

    private final CherryColaCore plugin;

    public BeeHiveListener(CherryColaCore plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent event) {
    	if (event.getRecipe().getResult().getType() == Material.HONEY_BLOCK) {
    		event.getInventory().getResult().setAmount(64);
		}
	}

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block != null && (block.getType() == Material.BEE_NEST || block.getType() == Material.BEEHIVE)) {
            Beehive beehive = (Beehive) block.getBlockData();
            if (beehive.getHoneyLevel() == beehive.getMaximumHoneyLevel()) {
                int randomAmount = new Random().nextInt(49) + 16;
                Location loc = block.getLocation();
                loc.getWorld().dropItemNaturally(loc.add(0, 0.5, 0), new ItemStack(Material.HONEYCOMB_BLOCK, randomAmount));

                new BukkitRunnable() {
					@Override
					public void run() {
						beehive.setHoneyLevel((int) (3 * beehive.getMaximumHoneyLevel()/4.0));
						block.setBlockData(beehive);
					}
				}.runTaskLaterAsynchronously(plugin, 1L);
            }
        }
    }

}
