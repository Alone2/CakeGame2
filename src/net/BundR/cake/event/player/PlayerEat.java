package net.BundR.cake.event.player;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;

public class PlayerEat implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getClickedBlock() != null) {
			if (event.getClickedBlock().getType().equals(Material.CAKE_BLOCK)) {
				FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
				String PlayerId = getPlayerConfigId.fromUUID(event.getPlayer().getUniqueId().toString());
				if(cfg.getString("Player" + PlayerId + ".noCake").equals("true")) {
					event.setCancelled(true);
				}
			}
		}
		
	}
}
