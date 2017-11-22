package net.BundR.cake;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class savePlayer {
	public static void save(FileConfiguration cfg, String PlayerId, Player player) {
		cfg.set("Player" + PlayerId + ".world", player.getLocation().getWorld());
		cfg.set("Player" + PlayerId + ".location.X", player.getLocation().getX());
		cfg.set("Player" + PlayerId + ".location.Y", player.getLocation().getY());
		cfg.set("Player" + PlayerId + ".location.Z", player.getLocation().getZ());
	}
	public static void get(FileConfiguration cfg, String PlayerId, Player player) {
		
	}
}
