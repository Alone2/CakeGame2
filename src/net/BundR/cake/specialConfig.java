package net.BundR.cake;


import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class specialConfig {
	
	public static FileConfiguration config(String Path) {
		
		File f = new File(Path);

		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			
		return cfg;
		
		
	}
	
	public static void saveConfig(FileConfiguration cfg, String Path) {
		
		File f = new File(Path);
		
		try {
			cfg.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
