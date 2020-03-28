package com.cherrycolagaming.info;

import com.cherrycolagaming.CherryColaCore;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ExplosionRegen {

	private Random RANDOM = new Random();

	public ExplosionRegen(List<BlockState> blocks, List<BlockState> dependentBlocks) {
		new BukkitRunnable() {
			@Override
			public void run() {
				BlockState state;
				if (!blocks.isEmpty()) {
					int index = RANDOM.nextInt(blocks.size());
					state = blocks.get(index);
					blocks.remove(index);
				} else if (!dependentBlocks.isEmpty()) {
					int index = RANDOM.nextInt(dependentBlocks.size());
					state = dependentBlocks.get(index);
					dependentBlocks.remove(index);
				} else {
					cancel();
					return;
				}
				Block block = state.getBlock();
				block.setType(state.getType());
				block.setBlockData(state.getBlockData());
			}
		}.runTaskTimer(CherryColaCore.getInstance(), 4L, 4L);
	}

}
