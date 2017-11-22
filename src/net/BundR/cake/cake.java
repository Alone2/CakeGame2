package net.BundR.cake;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.BundR.cake.commands.cake_c;
import net.BundR.cake.event.player.PlayerJoin;

public class cake extends JavaPlugin {

	public void onEnable() {
		PluginDescriptionFile pdffile = getDescription();
		Logger logger = getLogger();

		registerCommands();
		registerEvents();

		logger.info(pdffile.getName() + " wurde gestartet! (Version: " + pdffile.getVersion() + ")");
	
	}

	public void onDisable() {
		PluginDescriptionFile pdffile = getDescription();
		Logger logger = getLogger();
		this.saveConfig();
		logger.info(pdffile.getName() + " wurde gestoppt! (Version: " + pdffile.getVersion() + ")");
	}

	public void registerCommands() {

		getCommand("cake").setExecutor(new cake_c());

	}
	
	public void registerEvents() {

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);

	}
	
}
