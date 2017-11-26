package net.BundR.cake;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class savePlayer {
	public static void save(FileConfiguration cfg, String PlayerId, Player player) {
		cfg.set("Player" + PlayerId + ".world", player.getLocation().getWorld().getName());
		cfg.set("Player" + PlayerId + ".health", player.getHealth());
		cfg.set("Player" + PlayerId + ".level", player.getLevel());
		cfg.set("Player" + PlayerId + ".hunger", player.getFoodLevel());
		cfg.set("Player" + PlayerId + ".gamemode", player.getGameMode().name());
		cfg.set("Player" + PlayerId + ".location.X", player.getLocation().getX());
		cfg.set("Player" + PlayerId + ".location.Y", player.getLocation().getY());
		cfg.set("Player" + PlayerId + ".location.Z", player.getLocation().getZ());
		cfg.set("Player" + PlayerId + ".location.Yaw", player.getLocation().getYaw());
		cfg.set("Player" + PlayerId + ".location.Pitch", player.getLocation().getPitch());
		
		int zero = 0;
		cfg.set("Player" + PlayerId + ".potion", null);
		cfg.set("Player" + PlayerId + ".inventory", null);
		for (PotionEffect potion : player.getActivePotionEffects()) {
			zero = zero + 1;
			String start = "Player" + PlayerId + ".potion." + zero;
						
			cfg.set(start + ".amplifier", potion.getAmplifier());
		    cfg.set(start + ".duration", potion.getDuration());
		    cfg.set(start + ".type", potion.getType().getName());
		}
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
	public static void setOld(FileConfiguration cfg, String PlayerId, Player player) {
		org.bukkit.Location yLoc = Bukkit.getWorld(cfg.getString("Player" + PlayerId + ".world")).getSpawnLocation();
		yLoc.setX(cfg.getDouble("Player" + PlayerId + ".location.X"));
		yLoc.setY(cfg.getDouble("Player" + PlayerId + ".location.Y"));
		yLoc.setZ(cfg.getDouble("Player" + PlayerId + ".location.Z"));
		yLoc.setYaw(cfg.getInt("Player" + PlayerId + ".location.Yaw"));
		yLoc.setPitch(cfg.getInt("Player" + PlayerId + ".location.Pitch"));;
		player.teleport(yLoc);
		player.setHealth(cfg.getInt("Player" + PlayerId + ".health"));
		player.setFoodLevel(cfg.getInt("Player" + PlayerId + ".hunger"));
		player.setLevel(cfg.getInt("Player" + PlayerId + ".level"));
		player.setGameMode(GameMode.valueOf(cfg.getString("Player" + PlayerId + ".gamemode")));
		
		player.getInventory().clear();
		Inventory inv = player.getInventory();
		for (int i=0; i<inv.getSize(); i++) {		
			if (cfg.getString("Player" + PlayerId + ".inventory." + i + ".type") == null) continue;
		    
		    Short durability = Short.valueOf(cfg.getString("Player" + PlayerId + ".inventory." + i + ".durability"));
		    Material type = Material.getMaterial(cfg.getString("Player" + PlayerId + ".inventory." + i + ".type"));
		    ItemStack stack = new ItemStack(type, cfg.getInt("Player" + PlayerId + ".inventory." + i + ".amount"), durability);
		    inv.setItem(i, stack);
		}
		for (int zero=0; zero<30; zero++) {
			if (cfg.getString("Player" + PlayerId + ".potion." + zero + ".type") == null) continue;
		    PotionEffect potionE = new PotionEffect(PotionEffectType.getByName(cfg.getString("Player" + PlayerId + ".potion." + zero + ".type")), cfg.getInt("Player" + PlayerId + ".potion." + zero + ".duration"), cfg.getInt("Player" + PlayerId + ".potion." + zero + ".amplifier"));
		    player.addPotionEffect(potionE);
		}
		cfg.set("Player" + PlayerId + ".cake", "false");
		cfg.set("Player" + PlayerId + ".teamm8", "0");
		cfg.set("Player" + PlayerId + ".g-teamm8-t", 0);
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
	}
	public static void clear(Player player) {
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		for (PotionEffect potion : player.getActivePotionEffects()) {
			player.removePotionEffect(potion.getType());
		}
		player.setFoodLevel(1);
		player.setHealth(20);
		player.setLevel(0);
	}
}
