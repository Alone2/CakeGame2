package net.BundR.cake.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.BundR.cake.cakeduell;
import net.BundR.cake.getPlayerConfigId;
import net.BundR.cake.specialConfig;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class cake_c implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Du must ein Spieler sein um dies nutzen zu können!");
			return false;
		}

		int debug = 0;
		
		Player player = (Player) sender;

		FileConfiguration cfg = specialConfig.config("plugins//CakeGame//player.yml");
		FileConfiguration cfg2 = specialConfig.config("plugins//CakeGame//data.yml");
		String PlayerId = getPlayerConfigId.fromUUID(String.valueOf(player.getUniqueId()));

		if (cfg.getString("Player" + PlayerId + ".teamm8").equals("0")) {
			
			if (cfg2.getString("Cakeduell.end").equals("none")) {

			if (args.length == 0) {

				player.sendMessage(ChatColor.RED + "Fehler: /cake duell [name] <---");

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

						IChatBaseComponent comp2 = ChatSerializer.a("{\"text\":\" [JA]\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cake accept " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/cake accept " + args[1] + "\",\"color\":\"dark_green\"}]}}}");
						PacketPlayOutChat chat2 = new PacketPlayOutChat(comp2);
						((CraftPlayer) playerother).getHandle().playerConnection.sendPacket(chat2);

						IChatBaseComponent comp3 = ChatSerializer.a("{\"text\":\" [NEIN]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cake deny " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"/cake deny " + args[1] + "\",\"color\":\"dark_red\"}]}}}");
						PacketPlayOutChat chat3 = new PacketPlayOutChat(comp3);
						((CraftPlayer) playerother).getHandle().playerConnection.sendPacket(chat3);

						cfg.set("Player" + PlayerId + ".g-teamm8", args[1]);
						specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");

					} else if (on == 2) {
						player.sendMessage(ChatColor.RED + "Fehler: Du kannst mit dir selber kein Cake-duell machen!");
					} else {
						player.sendMessage(ChatColor.RED + "Fehler: " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " ist nicht online!");
					}

				} else if (args.length == 2 && args[0].equals("accept")) {

					if (on == 1) {
						Player playerother = player.getServer().getPlayer(args[1]);

						String PlayerId2 = getPlayerConfigId.fromUUID(String.valueOf(playerother.getUniqueId()));
						if (cfg.getString("Player" + PlayerId2 + ".g-teamm8").equals(player.getName())) {
							playerother.sendMessage(ChatColor.GREEN + "Deine Cake-duell Anfrage für " + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + " wurde akzeptiert!");
							player.sendMessage(ChatColor.GREEN + "Du hast die Cake-duell Anfrage von " + ChatColor.DARK_GREEN + playerother.getName() + ChatColor.GREEN + " akzeptiert!");

							cfg.set("Player" + PlayerId + ".teamm8", PlayerId2);
							cfg.set("Player" + PlayerId2 + ".teamm8", PlayerId);
							specialConfig.saveConfig(cfg, "plugins//CakeGame//player.yml");
							
							cakeduell.start(player,PlayerId, playerother, PlayerId2, 500);
						}
					}
				} else if (args.length == 2 && args[0].equals("deny")) {

					if (on == 1) {
						Player playerother = player.getServer().getPlayer(args[1]);
						playerother.sendMessage(ChatColor.RED + "Deine Cake-duell Anfrage für " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + " wurde abgelehnt!");
						player.sendMessage(ChatColor.RED + "Du hast die Cake-duell Anfrage von " + ChatColor.DARK_RED + playerother.getName() + ChatColor.RED + " abgelehnt!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Fehler: /cake duell [name] <---");
				}
				if (debug == 1) {
					if (args.length == 1 && args[0].equals("debug")) {
						//cake -> int -> erhöht sich mit Anzahl der Spiele
						cakeduell.start(player,PlayerId, player, PlayerId, 500);
						player.sendMessage("debug");
					}
				}
			}
			} else {
				player.sendMessage(ChatColor.RED + "Es findet gerade ein Cake-duell statt! Warte noch ein bisschen!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "Fehler: Du hast schon einen Gegner!");
		}
		
		return false;
		
	}

}
