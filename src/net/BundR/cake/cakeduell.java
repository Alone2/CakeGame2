package net.BundR.cake;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class cakeduell {

	public static void start(Player player, String playerID, Player playerother, String playerotherID) {
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		
		if (Bukkit.getWorld("cake-duell") == null){
			WorldCreator wc = new WorldCreator("cake-duell");
			wc.environment(Environment.NORMAL);
			wc.generateStructures(false);
			wc.type(WorldType.FLAT);
			Bukkit.broadcastMessage("§cEine neue Welt wird generiert! Vielleicht wird's in der nächsten Zeit laggen! (Das passiert nie wieder!)");
			wc.createWorld();
			Bukkit.broadcastMessage("§cDie generation der neuen Welt wurde beendet!");
		}
		savePlayer.save(cfg, playerID, player);
		savePlayer.save(cfg, playerotherID, playerother);
		
		player.teleport(Bukkit.getWorld("cake-duell").getSpawnLocation(), TeleportCause.PLUGIN);
		playerother.teleport(Bukkit.getWorld("cake-duell").getSpawnLocation());
		
		savePlayer.clear(playerother);
		
		savePlayer.setOld(cfg, playerotherID, playerother);
		
	
	}

}
