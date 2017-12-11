package net.BundR.cake.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.BundR.cake.cake;
import net.BundR.cake.cakeduell;
import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class cake_c implements CommandExecutor {
	
	private cake plugin;

	public cake_c(cake pl) {
		plugin = pl;
	}
	
	static FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
	final BukkitTask[] loop = new BukkitTask[Bukkit.getServer().getMaxPlayers() + 1];
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		FileConfiguration cfg4 = specialConfig.config("plugins//CakeGame//language.yml");
		String lang = cfg4.getString("lang");
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(cfg4.getString(lang + ".beaplayer"));
			return false;
		}
		
		Player player = (Player) sender;

		FileConfiguration cfg3 = specialConfig.config("plugins//CakeGame//player.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		String PlayerId = getPlayerConfigId.fromUUID(String.valueOf(player.getUniqueId()));
		
		if (cfg3.getString("Player" + PlayerId + ".g-teamm8").equals("0")) {
			
			for (int i = 0; i < Bukkit.getServer().getMaxPlayers(); i++) {
				if (cfg2.getInt("loop." + i) == 0) {
					cfg2.set("loop." + i, 1);
					cfg3.set("Player" + PlayerId + ".loopID", i);
					specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
					specialConfig.saveConfig(cfg3, "plugins//CakeGame//player.yml");
					break;
				}
			}
		} else {
			loop[cfg3.getInt("Player" + PlayerId + ".loopID")].cancel();
		}
		
		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		
		if (cfg.getString("Player" + PlayerId + ".teamm8").equals("0")) {
			
			if(cfg2.getInt("Cakeduell.number") < plugin.getConfig().getInt("Cakduellrunden")) {
			
			if (args.length == 0) {
				
				player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + cfg4.getString(lang + ".badcakecommand"));

			} else {
				int on = 0;
				if (args.length == 2) {
					
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getName().equals(args[1])) {
							if (!p.equals(player)) {
								on = 1;
							} else {
								on = 2;
							}	
						}

					}
				}
				if (args.length == 2 && args[0].equals("duell")) {
					if (on == 1) {
						Player playerother = player.getServer().getPlayer(args[1]);
						playerother.sendMessage(ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + " hat dich zu einem Cake-duell eingeladen! Hilfst du mit?");
						player.sendMessage(ChatColor.GREEN + "Du hast " + ChatColor.DARK_GREEN + playerother.getName() + ChatColor.GREEN + " zu einem Cake-duell eingeladen!");

						IChatBaseComponent comp2 = ChatSerializer.a("{\"text\":\" [" + cfg4.getString(lang + ".invitementyes") + "]\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cake accept " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/cake accept " + args[1] + "\",\"color\":\"dark_green\"}]}}}");
						PacketPlayOutChat chat2 = new PacketPlayOutChat(comp2);
						((CraftPlayer) playerother).getHandle().playerConnection.sendPacket(chat2);
						
						IChatBaseComponent comp3 = ChatSerializer.a("{\"text\":\" [" + cfg4.getString(lang + ".invitementno") + "]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cake deny " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/cake deny " + args[1] + "\",\"color\":\"dark_red\"}]}}}");
						PacketPlayOutChat chat3 = new PacketPlayOutChat(comp3);
						((CraftPlayer) playerother).getHandle().playerConnection.sendPacket(chat3);

						cfg.set("Player" + PlayerId + ".g-teamm8", args[1]);
						cfg.set("Player" + PlayerId + ".g-teamm8-t", 30);
						specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
						
						loop[cfg.getInt("Player" + PlayerId + ".loopID")] = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
							@Override
							public void run() {
								FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
								FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
								if (cfg.getInt("Player" + PlayerId + ".g-teamm8-t") <= 0) {
									cfg.set("Player" + PlayerId + ".g-teamm8", "0");
									cfg2.set("loop." + cfg.getInt("Player" + PlayerId + ".loopID"), 0);
									loop[cfg.getInt("Player" + PlayerId + ".loopID")].cancel();
								} else {
									cfg.set("Player" + PlayerId + ".g-teamm8-t", Integer.valueOf(cfg.getInt("Player" + PlayerId + ".g-teamm8-t") - 1));
								}
								specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
								specialConfig.saveConfig(cfg2, "plugins//CakeGame//data.yml");
							}
						}, 20 * 1L, 20 * 1);

					} else if (on == 2) {
						player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + cfg4.getString(lang + ".youcantplaywithyourself"));
					} else {
						player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + ChatColor.DARK_RED + args[1] + ChatColor.RED + " " + cfg4.getString(lang + ".isnotonline"));
					}

				} else if (args.length == 2 && args[0].equals("accept")) {

					if (on == 1) {
						Player playerother = player.getServer().getPlayer(args[1]);

						String PlayerId2 = getPlayerConfigId.fromUUID(String.valueOf(playerother.getUniqueId()));
						if (cfg.getString("Player" + PlayerId2 + ".g-teamm8").equals(player.getName())) {
							
							String[] Invitementfor = cfg4.getString(lang + ".invitementforaccept").split("@name"), Invitementof = cfg4.getString(lang + ".invitementofaccept").split("@name");
							int addition = (cfg2.getInt("Cakeduell.number") + 1)*100;
							playerother.sendMessage(ChatColor.GREEN + Invitementfor[0] + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + Invitementfor[1]);
							player.sendMessage(ChatColor.GREEN + Invitementof[0] + ChatColor.DARK_GREEN + playerother.getName() + ChatColor.GREEN + Invitementof[1]);

							cfg.set("Player" + PlayerId + ".teamm8", PlayerId2);
							cfg.set("Player" + PlayerId2 + ".teamm8", PlayerId);
							specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
							
							cakeduell.start(player,PlayerId, playerother, PlayerId2, addition);
						} else {
							player.sendMessage(ChatColor.RED + "Die Einladung von " + args[1] + " ist abgelaufen, oder war noch nie existent!");
						}
					}
				} else if (args.length == 2 && args[0].equals("deny")) {

					if (on == 1) {
						Player playerother = player.getServer().getPlayer(args[1]);
						String[] Invitementfor = cfg4.getString(lang + ".invitementfordeny").split("@name"), Invitementof = cfg4.getString(lang + ".invitementofdeny").split("@name");
						playerother.sendMessage(ChatColor.RED + Invitementfor[0] + ChatColor.DARK_RED + player.getName() + ChatColor.RED + Invitementfor[1]);
						player.sendMessage(ChatColor.RED + Invitementof[0] + ChatColor.DARK_RED + playerother.getName() + ChatColor.RED + Invitementof[1]);
					}
				} else {
					player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + cfg4.getString(lang + ".badcakecommand"));
				}
				
			}
			} else {
				player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + cfg4.getString(lang + ".toomany"));
			}
			
		} else {
			player.sendMessage(ChatColor.RED + cfg4.getString(lang + ".error") + cfg4.getString(lang + ".youhaveaopponent"));
		}
		
		return false;
		
	}

}
