package net.BundR.cake;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.BundR.cake.commands.cake_c;
import net.BundR.cake.event.player.PlayerEat;
import net.BundR.cake.event.player.PlayerJoin;

public class cake extends JavaPlugin {

	public void onEnable() {
		registerCommands();
		registerEvents();
		new cakeduell(this);
	}

	public void onDisable() {
		//this.saveConfig();
	}

	public void registerCommands() {

		getCommand("cake").setExecutor(new cake_c());

	}
	
	public void registerEvents() {

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerEat(), this);
	}
	
}
