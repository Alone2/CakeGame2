package net.BundR.cake.event.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;

public class PlayerMove implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		String PlayerId = getPlayerConfigId.fromUUID(event.getPlayer().getUniqueId().toString());
		if(cfg.getString("Player" + PlayerId + ".noCake").equals("true")) {
			event.setCancelled(true);
		}
		
	}
}
