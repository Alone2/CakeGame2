package net.BundR.cake;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class savePlayer {
	public static void save(FileConfiguration cfg, String PlayerId, Player player) {
		cfg.set("Player" + PlayerId + ".world", player.getLocation().getWorld().getName());
		cfg.set("Player" + PlayerId + ".health", player.getHealth());
		cfg.set("Player" + PlayerId + ".location.X", player.getLocation().getX());
		cfg.set("Player" + PlayerId + ".location.Y", player.getLocation().getY());
		cfg.set("Player" + PlayerId + ".location.Z", player.getLocation().getZ());
		
		Inventory inv = player.getInventory();
		for (int i=0; i<inv.getSize(); i++) {
			
			if (inv.getItem(i) == null) continue;
			
		    String start = "Player" + PlayerId + ".inventory." + i;
		    		    
		    cfg.set(start + ".amount", inv.getItem(i).getAmount());
		    cfg.set(start + ".durability", inv.getItem(i).getDurability());
		    cfg.set(start + ".type", String.valueOf(inv.getItem(i).getType()));
		}
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
		
	}
	public static void get(FileConfiguration cfg, String PlayerId, Player player) {
		
	}
}
