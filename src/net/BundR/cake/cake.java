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
import net.BundR.cake.ingame.startCake;

public class cake extends JavaPlugin {

	public void onEnable() {
		registerCommands();
		registerEvents();
		registerConfig();
		new cakeduell(this);
		new startCake(this);
	}

	public void onDisable() {
		FileConfiguration cfg3 = specialConfig.config("plugins//CakeGame//language.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");

		for (int i = 0; i < this.getConfig().getInt("Cakduellrunden"); i++) {
			if (cfg2.getInt("Cakeduell.number." + i) == 1) {
				for(int z = 0; z < cfg2.getInt("Cakeduell." + Integer.valueOf(i) + ".wieviele"); z++) {
					int x = z + 1;
					Player player = Bukkit.getPlayer(cfg2.getString("Cakeduell." + Integer.valueOf(i) + ".player." + x + ".name"));
					String PlayerId = cfg2.getString("Cakeduell." + Integer.valueOf(i) + ".player." + x + ".PlayerId");
					cfg.set("Player" + PlayerId + ".noCake", "false");
					specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
					savePlayer.setOld(cfg, PlayerId, player);
					player.sendMessage(cfg3.getString(cfg3.getString("lang") + ".kickmessage"));
				}
			}
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
		File specialf2 = new File(getDataFolder(), "language.yml");

        if (!specialf.exists()) {
            specialf.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        
        if (!specialf2.exists()) {
            specialf2.getParentFile().mkdirs();
            saveResource("language.yml", false);
        }
        FileConfiguration cfg3 = specialConfig.config("plugins//CakeGame//language.yml");
        if (cfg3.getDouble("ver") < 1.0) {
        	specialConfig.saveConfig(cfg3, "plugins//CakeGame//language_old.yml");
        	specialf2.getParentFile().mkdirs();
            saveResource("language.yml", true);
        }
        
        FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
        FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
        
        for (int i = 0; i < Bukkit.getServer().getMaxPlayers(); i++) {
        	cfg2.set("loop." + i, 0);
        }
        for (int i = 0; i < cfg2.getInt("WieViele"); i++) {
        	cfg.set("Player" + Integer.valueOf(i + 1) + ".g-teamm8", "0");
        }
        for (int i = 0; i < this.getConfig().getInt("Cakduellrunden"); i++) {
			cfg2.set("Cakeduell.number." + i, 0);		
		}
        
		//cfg2.set("Cakeduell.number", 0);
		specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml"); 
		specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml"); 
		
	}
	
}
