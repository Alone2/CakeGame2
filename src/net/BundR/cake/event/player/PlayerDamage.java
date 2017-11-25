package net.BundR.cake.event.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;

public class  PlayerDamage implements Listener {
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			
			FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
			String PlayerId = getPlayerConfigId.fromUUID(event.getEntity().getUniqueId().toString());
			if(cfg.getString("Player" + PlayerId + ".cake").equals("true")) {
				event.setCancelled(true);
			}
		}
	}

}
