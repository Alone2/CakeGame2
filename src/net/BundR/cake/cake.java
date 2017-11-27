package net.BundR.cake;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.BundR.cake.commands.cake_c;
import net.BundR.cake.event.player.PlayerDamage;
import net.BundR.cake.event.player.PlayerEat;
import net.BundR.cake.event.player.PlayerJoin;
import net.BundR.cake.event.player.PlayerMove;
import net.md_5.bungee.api.ChatColor;

public class cake extends JavaPlugin {

	public void onEnable() {
		registerCommands();
		registerEvents();
		registerConfig();
		new cakeduell(this);
	}

	public void onDisable() {
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");

		for (int i = 0; i < cfg2.getInt("Cakeduell.number"); i++) {
			Player player = Bukkit.getPlayer(cfg2.getString("Cakeduell." + Integer.valueOf(i+1) + ".player.1.name"));
			Player player2 = Bukkit.getPlayer(cfg2.getString("Cakeduell." + Integer.valueOf(i+1) + ".player.2.name"));
			String PlayerId = cfg2.getString("Cakeduell." + Integer.valueOf(i+1) + ".player.1.PlayerId");
			String PlayerId2 = cfg2.getString("Cakeduell." + Integer.valueOf(i+1) + ".player.2.PlayerId");
			cfg.set("Player" + PlayerId + ".noCake", "false");
	    	cfg.set("Player" + PlayerId2 + ".noCake", "false");
	    	specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
			savePlayer.setOld(cfg, PlayerId, player);
			savePlayer.setOld(cfg, PlayerId2, player2);
			player.sendMessage(ChatColor.RED + "Du wurdest aus dem Spiel gekickt, da der Server neu startet oder herunterfährt!");
			player2.sendMessage(ChatColor.RED + "Du wurdest aus dem Spiel gekickt, da der Server neu startet oder herunterfährt!");
		}
	}

	public void registerCommands() {

		getCommand("cake").setExecutor(new cake_c(this));

	}
	
	public void registerEvents() {

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerEat(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerDamage(), this);
	}
	
	public void registerConfig() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		File specialf = new File(getDataFolder(), "data.yml");

        if (!specialf.exists()) {
            specialf.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        
        FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		//cfg2.set("Cakeduell.end", "none");
		cfg2.set("Cakeduell.number", 0);
		specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml"); 
		
	}
	
}
