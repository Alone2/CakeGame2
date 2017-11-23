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
			Bukkit.broadcastMessage("§cEine neue Welt wird generiert! Vielleicht wird's in der nächsten Zeit laggen!");
			wc.createWorld();
			Bukkit.broadcastMessage("§cDie generation der neuen Welt wurde beendet!");
		}
		savePlayer.save(cfg, playerID, player);
		player.teleport(Bukkit.getWorld("cake-duell").getSpawnLocation(), TeleportCause.PLUGIN);
		
		savePlayer.save(cfg, playerotherID, playerother);
		playerother.teleport(Bukkit.getWorld("cake-duell").getSpawnLocation(), TeleportCause.PLUGIN);
		
		org.bukkit.Location yLoc = Bukkit.getWorld("world").getSpawnLocation();
		yLoc.setX(100);
		yLoc.setY(100);
		yLoc.setZ(100);
		//player.teleport(yLoc, TeleportCause.PLUGIN);
		
	
	}

}
