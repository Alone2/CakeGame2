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
	
	static FileConfiguration config = specialConfig.config("plugins//CakeGame//config.yml");
	static int[] Zahl = new int[config.getInt("Cakduellrunden") + 1];

	final static BukkitTask[] loop1 = new BukkitTask[config.getInt("Cakduellrunden") + 1];
	final static BukkitTask[] loop2 = new BukkitTask[config.getInt("Cakduellrunden") + 1];
	
	public static void start(Player player, String playerID, Player playerother, String playerotherID, int addition) {
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		FileConfiguration cfg4 = specialConfig.config("plugins//CakeGame//language.yml");
		String lang = cfg4.getString("lang");
		
		int number = cfg2.getInt("Cakeduell.number") + 1;
		cfg2.set("Cakeduell.number", number);
		cfg2.set("Cakeduell." + number + ".end", "false");
		cfg2.set("Cakeduell." + number + ".player.1.name", player.getName());
		cfg2.set("Cakeduell." + number + ".player.1.PlayerId", playerID);
		cfg2.set("Cakeduell." + number + ".player.2.name", playerother.getName());
		cfg2.set("Cakeduell." + number + ".player.2.PlayerId", playerotherID);
		cfg.set("Player" + playerID + ".cake", "true");
		cfg.set("Player" + playerotherID + ".cake", "true");
		specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
		
		Zahl[number] = 0;
		if (Bukkit.getWorld("cake-duell") == null){
			WorldCreator wc = new WorldCreator("cake-duell");
			wc.environment(Environment.NORMAL);
			wc.generateStructures(false);
			wc.type(WorldType.FLAT);
			player.sendMessage(cfg4.getString(lang + ".generation"));
			playerother.sendMessage(cfg4.getString(lang + ".generation"));
			wc.createWorld();
			player.sendMessage(cfg4.getString(lang + ".generationfinished"));
			playerother.sendMessage(cfg4.getString(lang + ".generationfinished"));
		}
		Bukkit.getWorld("cake-duell").setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getWorld("cake-duell").setTime(5000);
		
		run(player, playerID, cfg, addition);
		run(playerother, playerotherID, cfg, addition + 5);
		loop1[number] = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				Zahl[number] = Zahl[number] + 1;
				if (Zahl[number] < 11) {
					player.sendTitle(ChatColor.AQUA + String.valueOf((Zahl[number] - 11)*-1),"", 20, 80, 20);
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING , 3.0F, 2F);
					playerother.sendTitle(ChatColor.AQUA + String.valueOf((Zahl[number] - 11)*-1),"", 20, 80, 20);
					playerother.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING , 3.0F, 2F);
				} else if (Zahl[number] == 11) {
					player.sendTitle(ChatColor.DARK_AQUA + "EAT","", 20, 80, 20);
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP , 3.0F, 0F);
					playerother.sendTitle(ChatColor.DARK_AQUA + "EAT","", 20, 80, 20);
					playerother.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP , 3.0F, 0F);
					FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
					cfg.set("Player" + playerID + ".noCake", "false");
					cfg.set("Player" + playerotherID + ".noCake", "false");
					specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
				} else {
					loop1[number].cancel();
				}
			}
		}, (20 * 1L), 20 * 1);
		
		loop2[number] = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
				FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
				ingame(player, playerID, playerother, playerotherID, cfg2, addition, cfg, number, lang, cfg4);
				ingame(playerother, playerotherID, player, playerID, cfg2, addition + 5, cfg, number, lang, cfg4);
				FileConfiguration cfg3 = specialConfig.config("plugins//CakeGame//player.yml");
				FileConfiguration cfg4 = specialConfig.config("plugins//CakeGame//data.yml");
				if (cfg4.getString("Cakeduell." + number + ".end").equals("true")) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					    public void run() {	
					    	cfg3.set("Player" + playerID + ".noCake", "false");
					    	cfg3.set("Player" + playerotherID + ".noCake", "false");
					    	FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
					    	cfg4.set("Cakeduell.number", cfg2.getInt("Cakeduell.number") - 1);
							specialConfig.saveConfig(cfg3, "plugins//CakeGame//player.yml");
							specialConfig.saveConfig(cfg4, "plugins//CakeGame//data.yml");
							
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
		}, (1L), 5);
				
	}
	private static void run(Player player, String playerID, FileConfiguration cfg, int addition) {
		
		savePlayer.save(cfg, playerID, player);
		
		int height = plugin.getConfig().getInt("Height");
		if (height > 248) {
			height = 248;
		}
		
		Location loc = Bukkit.getWorld("cake-duell").getSpawnLocation();
		loc.setZ(loc.getZ() + addition);
		buildcake.build(loc, height);
		
		Location loctp = Bukkit.getWorld("cake-duell").getSpawnLocation();
		loctp.setY(loctp.getY() + height + 0.5);
		loctp.setX(loctp.getX() + 0.5);
		loctp.setZ(loctp.getZ() + 0.5 + addition);
		loctp.setPitch(90);
		loctp.setYaw(-90);
		
		cfg.set("Player" + playerID + ".noCake", "true");
		player.teleport(loctp);
		savePlayer.clear(player);
		
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
		
	}
	private static void ingame(Player player, String playerID, Player playerother, String playerotherID, FileConfiguration cfg2, int addition, FileConfiguration cfg, int number,String lang, FileConfiguration cfg4) {
		player.setFoodLevel(1);
		boolean wonB = false;
		String wonS = null;
		
		Location pl = player.getLocation();
		pl.setY(pl.getY() - 1);
		if (pl.getBlock().getType().equals(Material.SANDSTONE)) {
			wonB = true;
			wonS = playerother.getName();
		}
		Location CakeLoc = Bukkit.getWorld("cake-duell").getSpawnLocation();
		CakeLoc.setY(CakeLoc.getY() + 4);
		CakeLoc.setZ(CakeLoc.getZ() + addition);
		if (CakeLoc.getBlock().getType().equals(Material.AIR)) {
			wonB = true;
			wonS = player.getName();
		}
		if (!player.isOnline()) {
			loop1[number].cancel();
			wonB = true;
			wonS = playerother.getName();
		}
		if (wonB == true) {
			player.sendTitle(ChatColor.DARK_RED + wonS,ChatColor.RED + cfg4.getString(lang + ".won"), 20, 80, 20);
			playerother.sendTitle(ChatColor.DARK_GREEN + wonS,ChatColor.GREEN + cfg4.getString(lang + ".won"), 20, 80, 20);
			loop2[number].cancel();
			cfg2.set("Cakeduell." + number + ".end", "true");
			specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
		}
	}

}
