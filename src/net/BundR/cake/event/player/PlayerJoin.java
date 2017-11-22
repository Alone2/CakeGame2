package net.BundR.cake.event.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;


public class PlayerJoin implements Listener {

	String PlayerId = "BUG";

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		
			
		if (getPlayerConfigId.fromUUID(p.getUniqueId().toString()).equals("BUG")) {
			int WieViele = cfg2.getInt("WieViele");
			
			cfg.set("Player" + String.valueOf(WieViele + 1) + ".UUID", String.valueOf(p.getUniqueId()));
			cfg.set("Player" + String.valueOf(WieViele + 1) + ".name", String.valueOf(p.getName()));
			cfg.set("Player" + String.valueOf(WieViele + 1) + ".teamm8", "");
			cfg2.set("WieViele", WieViele + 1);
			specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml"); 
			specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		}
	}
}
