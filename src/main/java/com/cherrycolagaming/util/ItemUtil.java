package com.cherrycolagaming.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class ItemUtil {
    private JavaPlugin    plugin;
    private MessagingUtil messagingUtil;
    
    public ItemUtil(JavaPlugin plugin, MessagingUtil messagingUtil) {
        this.plugin = plugin;
        this.messagingUtil = messagingUtil;
    }
    
    public static int getInventorySpace(Inventory inventory, ItemStack item) {
        int space = 0;
        int itemSize = item.getAmount();
        
        for(int invSlot = 0; invSlot < inventory.getSize(); ++invSlot) {
            ItemStack invItem = inventory.getItem(invSlot);
            if (invItem != null && invItem.getType() != Material.AIR) {
                if (invItem.isSimilar(item)) {
                    space += Math.min(invItem.getMaxStackSize() - invItem.getAmount(), itemSize);
                }
            } else {
                space = itemSize;
            }
            
            if (space >= itemSize) {
                space = itemSize;
                break;
            }
        }
        
        return space;
    }
    
    public ItemStack getItemStack(String path) {
        return getItemStack(this.plugin.getConfig(), path);
    }

    public static ItemStack getItemStack(FileConfiguration config, String path) {
        ItemStack item = null;
        if (!path.endsWith(".")) {
            path = path + ".";
        }

        try {
            Material mat    = Material.valueOf(config.getString(path + "material", "NULL"));
            int      amount = config.getInt(path + "amount", 1);
            item = new ItemStack(mat, amount);
            short data = (short)config.getInt(path + "data", item.getDurability());
            item.setDurability(data);
            ItemMeta itemMeta = item.getItemMeta();
            String   name     = config.getString(path + "name", "");
            if (!name.equals("")) {
                ((ItemMeta)itemMeta).setDisplayName(MessagingUtil.format(name));
            }
            
            List<String> loreLines = config.getStringList(path + "lore");
            Iterator var10;
            String enchantInfo;
            if (!loreLines.isEmpty()) {
                List<String> lore = new ArrayList();
                var10 = loreLines.iterator();
                
                while(var10.hasNext()) {
                    enchantInfo = (String)var10.next();
                    lore.add(MessagingUtil.format(enchantInfo));
                }
                
                itemMeta.setLore(lore);
            }
            
            List<String> enchants = config.getStringList(path + "enchants");
            var10 = enchants.iterator();
            
            while(var10.hasNext()) {
                enchantInfo = (String)var10.next();
                int         colonIndex  = enchantInfo.indexOf(":");
                Enchantment enchantment = Enchantment.getByName(enchantInfo.substring(0, colonIndex));
                if (enchantment != null) {
                    int level = Integer.parseInt(enchantInfo.substring(colonIndex + 1, enchantInfo.length()));
                    ((ItemMeta)itemMeta).addEnchant(enchantment, level, true);
                }
            }
            
            List<String> itemFlags = config.getStringList(path + "item-flags");
            Iterator var18 = itemFlags.iterator();
            
            String pluginName;
            while(var18.hasNext()) {
                pluginName = (String)var18.next();
                ((ItemMeta)itemMeta).addItemFlags(new ItemFlag[]{ItemFlag.valueOf(pluginName)});
            }
            
            if (mat == Material.PLAYER_HEAD && config.isSet(path + "skull-owner") && itemMeta == null) {
                applyCustomHead(itemMeta, UUID.fromString(config.getString(path + "skull-owner")));
                pluginName = ChatColor.stripColor(MessagingUtil.format(config.getString("prefix", "ItemUtil 1").split("\\s")[0]));
                Bukkit.getLogger().warning("[" + pluginName + "] Failed to load skull skin-value @ " + path);
            }
            
            if ((mat == Material.LEATHER_BOOTS || mat == Material.LEATHER_CHESTPLATE || mat == Material.LEATHER_HELMET || mat == Material.LEATHER_LEGGINGS) && config.isSet(path + "color")) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)itemMeta;
                leatherArmorMeta.setColor(Color.fromRGB(config.getInt(path + "color")));
                itemMeta = leatherArmorMeta;
            }
            
            item.setItemMeta((ItemMeta)itemMeta);
            return item;
        } catch (IllegalArgumentException var15) {
            return null;
        }
    }
    
    public static SkullMeta applyCustomHead(ItemMeta itemMeta, UUID uuid) {
        SkullMeta skullMeta = (SkullMeta)itemMeta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        return skullMeta;
    }
    
    public int getSlot(String path) {
        return getSlot(this.plugin.getConfig(), path);
    }
    
    public static int getSlot(FileConfiguration config, String path) {
        if (!path.endsWith(".")) {
            path = path + ".";
        }
        
        int xCord = config.getInt(path + "x-cord", -1);
        int yCord = config.getInt(path + "y-cord", -1);
        return xCord != -1 && yCord != -1 ? getSlot(xCord, yCord) : config.getInt(path + "slot", 0);
    }
    
    public static int getSlot(int xCord, int yCord) {
        return (yCord - 1) * 9 + xCord - 1;
    }
    
    public static int getX(int slot) {
        return slot % 9 + 1;
    }
    
    public static int getY(int slot) {
        return slot / 9 + 1;
    }
    
    public static boolean itemIsReal(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
    
    public static void addDurabilityGlow(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        item.setItemMeta(itemMeta);
    }
    
    public static void storeItemStack(ItemStack item, FileConfiguration config, String path) {
        if (!path.endsWith(".")) {
            path = path + ".";
        }
        
        config.set(path + "material", item.getType().name());
        config.set(path + "amount", item.getAmount());
        config.set(path + "data", item.getDurability());
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            config.set(path + "name", itemMeta.getDisplayName());
        }
        
        if (itemMeta.hasLore()) {
            config.set(path + "lore", itemMeta.getLore());
        }
        
        ArrayList itemFlags;
        Iterator var5;
        if (itemMeta.hasEnchants()) {
            itemFlags = new ArrayList();
            var5 = itemMeta.getEnchants().entrySet().iterator();
            
            while(var5.hasNext()) {
                Entry<Enchantment, Integer> enchant = (Entry)var5.next();
                itemFlags.add(((Enchantment)enchant.getKey()).getName() + ":" + enchant.getValue());
                config.set(path + "enchants", itemFlags);
            }
        }
        
        itemFlags = new ArrayList();
        var5 = itemMeta.getItemFlags().iterator();
        
        while(var5.hasNext()) {
            ItemFlag itemFlag = (ItemFlag)var5.next();
            itemFlags.add(itemFlag.name());
        }
        
        if (!itemFlags.isEmpty()) {
            config.set(path + "item-flags", itemFlags);
        }
        
        if (item.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta)itemMeta;
            config.set(path + "skull-owner", skullMeta.getOwningPlayer().getUniqueId().toString());
        }
        
    }
}
