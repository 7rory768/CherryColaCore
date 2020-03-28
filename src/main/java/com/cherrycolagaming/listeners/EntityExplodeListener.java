package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import com.cherrycolagaming.info.ExplosionRegen;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityExplodeListener implements Listener {

	private final HashSet<Material> ignoredBlocks = new HashSet<>(), dependentBlocks = new HashSet<>();

	public EntityExplodeListener(CherryColaCore plugin) {
		for (Material mat : Material.values()) {
			String name = mat.name();
			if (name.endsWith("BED")) {
				ignoredBlocks.add(mat);
			} else if (name.endsWith("BUTTON") || name.endsWith("SIGN")) {
				dependentBlocks.add(mat);
			}
		}

		dependentBlocks.add(Material.TORCH);
		dependentBlocks.add(Material.WALL_TORCH);
		dependentBlocks.add(Material.LANTERN);

		dependentBlocks.add(Material.REDSTONE_TORCH);
		dependentBlocks.add(Material.REDSTONE_WALL_TORCH);

		dependentBlocks.add(Material.REDSTONE_WIRE);
		dependentBlocks.add(Material.REPEATER);
		dependentBlocks.add(Material.LEVER);

		dependentBlocks.add(Material.RAIL);
		dependentBlocks.add(Material.ACTIVATOR_RAIL);
		dependentBlocks.add(Material.DETECTOR_RAIL);
		dependentBlocks.add(Material.POWERED_RAIL);

		dependentBlocks.add(Material.LADDER);

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			List<BlockState> blocks = new ArrayList<>(), dependentBlocks = new ArrayList<>();
			for (Block block : e.blockList()) {
				if (!isIgnoredBlock(block)) {
					if (dependentBlock(block)) {
						dependentBlocks.add(block.getState());
					} else {
						blocks.add(block.getState());
					}
					block.setType(Material.AIR);
				}
			}
			e.setYield(0);
			e.blockList().clear();
			new ExplosionRegen(blocks, dependentBlocks);
		}
	}

	boolean isIgnoredBlock(Block block) {
		return block.getState() instanceof Container || ignoredBlocks.contains(block.getType());
	}

	boolean dependentBlock(Block block) {
		return dependentBlocks.contains(block.getType());
	}


}
