package net.BundR.cake;

import org.bukkit.configuration.file.FileConfiguration;

public class getPlayerConfigId {
	
	public static String fromName(String Name) {
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		
		String PlayerId = "BUG";
		for (int i = 0; i < 8; i++) { 
			int e = i + 1; 
			String Config = String.valueOf(cfg.getString("Player" + String.valueOf(e) + ".name")); 

			if (Config.equals(Name)) {

				PlayerId = String.valueOf(e); 

			} 

		}
		
		return PlayerId;
		
	}
	
	
	public static String fromUUID(String UUID) {
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		
		String PlayerId = "BUG";
		for (int i = 0; i < 8; i++) { 
			int e = i + 1; 
			String Config = String.valueOf(cfg.getString("Player" + String.valueOf(e) + ".UUID")); 

			if (Config.equals(UUID)) {

				PlayerId = String.valueOf(e); 

			} 

		}
		
		return PlayerId;
		
	}
	
}
