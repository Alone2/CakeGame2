package net.BundR.cake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
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
	
	public static void start(Player player, String playerID, Player playerother, String playerotherID, int addition) {
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		cfg2.set("Cakeduell.end", "false");
		cfg2.set("Cakeduell.player.1.name", player.getName());
		cfg2.set("Cakeduell.player.1.PlayerId", playerID);
		cfg2.set("Cakeduell.player.2.name", playerother.getName());
		cfg2.set("Cakeduell.player.2.PlayerId", playerotherID);
		cfg.set("Player" + playerID + ".cake", "true");
		cfg.set("Player" + playerotherID + ".cake", "true");
		specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
		
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
		Bukkit.getWorld("cake-duell").setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getWorld("cake-duell").setTime(5000);
		
		run(player, playerID, cfg, addition);
		run(playerother, playerotherID, cfg, addition + 5);
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
				} else {
					loop1.cancel();
				}
			}
		}, (20 * 1L), 20 * 1);
		
		loop2 = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				ingame(player, playerID, playerother, playerotherID, cfg2, addition, cfg);
				ingame(playerother, playerotherID, player, playerID, cfg2, addition + 5, cfg);
				
				if (cfg2.getString("Cakeduell.end").equals("true")) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					    public void run() {	
					    	cfg.set("Player" + playerID + ".noCake", "false");
					    	cfg.set("Player" + playerotherID + ".noCake", "false");
					    	cfg2.set("Cakeduell.end", "none");
							specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
							specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
							
					    	if (player.isOnline()) {
					    		savePlayer.setOld(cfg, playerID, player);
					    	}
					    	if (playerother.isOnline()) {
					    		savePlayer.setOld(cfg, playerotherID, playerother);   
					    	}				    	
					    }
					}, 20L);
				}
				
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
	private static void ingame(Player player, String playerID, Player playerother, String playerotherID, FileConfiguration cfg2, int addition, FileConfiguration cfg) {
		player.setFoodLevel(1);
		
		Location pl = player.getLocation();
		pl.setY(pl.getY() - 1);
		if (pl.getBlock().getType().equals(Material.RED_SANDSTONE)) {
			player.sendTitle(ChatColor.DARK_RED + playerother.getName(),ChatColor.RED + "hat gewonnen!", 20, 80, 20);
			playerother.sendTitle(ChatColor.DARK_GREEN + playerother.getName(),ChatColor.GREEN + "hat gewonnen!", 20, 80, 20);
			loop2.cancel();
			cfg2.set("Cakeduell.end", "true");
			specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		}
		Location CakeLoc = Bukkit.getWorld("cake-duell").getSpawnLocation();
		CakeLoc.setY(CakeLoc.getY() + 4);
		CakeLoc.setZ(CakeLoc.getZ() + addition);
		if (CakeLoc.getBlock().getType().equals(Material.AIR)) {
			player.sendTitle(ChatColor.DARK_GREEN + player.getName(),ChatColor.GREEN + "hat gewonnen!", 20, 80, 20);
			playerother.sendTitle(ChatColor.DARK_RED + player.getName(),ChatColor.RED + "hat gewonnen!", 20, 80, 20);
			loop2.cancel();
			cfg2.set("Cakeduell.end", "true");
			specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		}
		if (!player.isOnline()) {
			loop1.cancel();
			player.sendTitle(ChatColor.DARK_RED + playerother.getName(),ChatColor.RED + "hat gewonnen!", 20, 80, 20);
			playerother.sendTitle(ChatColor.DARK_GREEN + playerother.getName(),ChatColor.GREEN + "hat gewonnen!", 20, 80, 20);
			loop2.cancel();
			cfg2.set("Cakeduell.end", "true");
			specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		}
	}

}
