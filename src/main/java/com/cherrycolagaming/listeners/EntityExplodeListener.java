package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityExplodeListener implements Listener {

	private final CherryColaCore plugin;
	private final Random RANDOM = new Random();

	private List<BlockState> blocks = new ArrayList<>();

	public EntityExplodeListener(CherryColaCore plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		new BukkitRunnable() {
			@Override
			public void run() {
				if (!blocks.isEmpty()) {
					int index = RANDOM.nextInt(blocks.size());
					BlockState state = blocks.get(index);
					Block block = state.getBlock();
					block.setType(state.getType());
					block.setBlockData(state.getBlockData());
					blocks.remove(index);
				}
			}
		}.runTaskTimer(plugin, 5L, 5L);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			for (Block block : e.blockList()) {
				if (!(block.getState() instanceof Container)) {
					blocks.add(block.getState());
					block.setType(Material.AIR);
				}
			}
			e.blockList().clear();
		}
	}


}
