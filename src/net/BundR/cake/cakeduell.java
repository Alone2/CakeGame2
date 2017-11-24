package net.BundR.cake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.BundR.cake.build.buildcake;

public class cakeduell {
	
	private static cake plugin;

	public cakeduell(cake pl) {
		plugin = pl;
	}
	
	static int Zahl = 0;
	
	private static BukkitTask loop1;
	private static BukkitTask loop2;
	
	public static void start(Player player, String playerID, Player playerother, String playerotherID) {
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		Zahl = 0;
		if (Bukkit.getWorld("cake-duell") == null){
			WorldCreator wc = new WorldCreator("cake-duell");
			wc.environment(Environment.NORMAL);
			wc.generateStructures(false);
			wc.type(WorldType.FLAT);
			Bukkit.broadcastMessage("§cEine neue Welt wird generiert! Vielleicht wird's in der nächsten Zeit laggen! (Das passiert nie wieder!)");
			wc.createWorld();
			Bukkit.broadcastMessage("§cDie generation der neuen Welt wurde beendet!");
		}
		run(player, playerID, cfg, 0);
		run(playerother, playerotherID, cfg, 5);
		loop1 = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				Zahl = Zahl + 1;
				if (Zahl < 11) {
					player.sendTitle(ChatColor.AQUA + String.valueOf((Zahl - 11)*-1),"", 20, 80, 20);
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING , 3.0F, 2F);
					playerother.sendTitle(ChatColor.AQUA + String.valueOf((Zahl - 11)*-1),"", 20, 80, 20);
					playerother.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING , 3.0F, 2F);
				} else if (Zahl == 11) {
					player.sendTitle(ChatColor.DARK_AQUA + "EAT","", 20, 80, 20);
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP , 3.0F, 0F);
					playerother.sendTitle(ChatColor.DARK_AQUA + "EAT","", 20, 80, 20);
					playerother.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP , 3.0F, 0F);
					cfg.set("Player" + playerID + ".noCake", "false");
					cfg.set("Player" + playerotherID + ".noCake", "false");
					specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
					player.setGameMode(GameMode.SURVIVAL);
					playerother.setGameMode(GameMode.SURVIVAL);
				} else {
					loop1.cancel();
				}
			}
		}, (20 * 1L), 20 * 1);
		
		loop2 = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				ingame(player, playerother);
				ingame(playerother, player);
			}
		}, (1L), 1);
		
		//player.teleport(Bukkit.getWorld("cake-duell").getSpawnLocation(), TeleportCause.PLUGIN);
		//savePlayer.setOld(cfg, playerotherID, playerother);
		
	
	}
	private static void run(Player player, String playerID, FileConfiguration cfg, int addition) {
		
		savePlayer.save(cfg, playerID, player);
		
		int height = 50;
		Location loc = Bukkit.getWorld("cake-duell").getSpawnLocation();
		loc.setZ(loc.getZ() + addition);
		buildcake.build(loc, height);
		
		Location loctp = Bukkit.getWorld("cake-duell").getSpawnLocation();
		loctp.setY(loctp.getY() + height + 1);
		loctp.setX(loctp.getX() + 0.5);
		loctp.setZ(loctp.getZ() + 0.5 + addition);
		loctp.setPitch(90);
		loctp.setYaw(-90);
		
		cfg.set("Player" + playerID + ".noCake", "true");
		player.teleport(loctp);
		savePlayer.clear(player);
		
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
		
	}
	private static void ingame(Player player, Player playerother) {
		player.setFoodLevel(1);
		
		Location pl = player.getLocation();
		pl.setY(pl.getY() - 1);
		if (pl.getBlock().getType().equals(Material.RED_SANDSTONE)) {
			player.sendMessage("TEST");
			loop2.cancel();
		}
		if (pl.getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
			player.sendMessage("gewonnen");
			playerother.sendMessage("du nicht!");
			loop2.cancel();
		}
	}

}
