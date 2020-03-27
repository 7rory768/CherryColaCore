package com.cherrycolagaming.managers;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IronGolemManager {

	private List<UUID> waitingToFall = new ArrayList<>();

	public boolean isWaitingToFall(Entity entity) {
		return waitingToFall.contains(entity.getUniqueId());
	}

	public void addWaitingToFall(Entity entity) {
		waitingToFall.add(entity.getUniqueId());
	}

	public void removeWaitToFall(Entity entity) {
		waitingToFall.remove(entity.getUniqueId());
	}

}
