package com.cherrycolagaming.listeners;

import com.cherrycolagaming.CherryColaCore;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ShearSheepListener implements Listener {

    public ShearSheepListener(CherryColaCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        if (event.getEntity() instanceof Sheep) {
            Sheep sheep = (Sheep) event.getEntity();
            DyeColor dyeColor = sheep.getColor();
            try {
                Material mat = Material.getMaterial(dyeColor.name() + "_WOOL");
                sheep.getLocation().getWorld().dropItemNaturally(sheep.getLocation(), new ItemStack(mat, (new Random().nextInt(16))));
            } catch (Exception e) {}
        }
    }
}
