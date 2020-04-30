package com.cherrycolagaming.managers;

import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IronGolemManager {

    private Set<UUID> waitingToFall = new HashSet<>();

    public boolean isWaitingToFall(Entity entity) {
        return waitingToFall.contains(entity.getUniqueId());
    }

    public void addWaitingToFall(Entity entity) {
        if (!isWaitingToFall(entity)) {
            waitingToFall.add(entity.getUniqueId());
        }
    }

    public void removeWaitToFall(Entity entity) {
        waitingToFall.remove(entity.getUniqueId());
    }

}
