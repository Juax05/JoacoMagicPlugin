package joaco.joacomagicplugin.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class ComandoPrincipal implements CommandExecutor {
private JoacoMagicPlugin plugin;
	
	public ComandoPrincipal(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" No podés ejecutar este comando desde la consola!");
			return false;
		}else {
			Player jugador = (Player) sender;
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("version")) {
					jugador.sendMessage(plugin.nombre+ChatColor.WHITE+" La version del plugin es: "+ChatColor.RED+plugin.version);
					return true;
				}
				else if(args[0].equalsIgnoreCase("spawn")) {
					FileConfiguration config = plugin.getConfig();
					if(config.contains("Config.Spawn.x")) {
						double x = Double.valueOf(config.getString("Config.Spawn.x"));
						double y = Double.valueOf(config.getString("Config.Spawn.y"));
						double z = Double.valueOf(config.getString("Config.Spawn.z"));
						float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
						float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
						World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
						Location l = new Location(world, x, y, z, yaw, pitch);    //252 64 260 -90 0
						jugador.teleport(l);
						return true;
					}else {
						jugador.sendMessage(plugin.nombre+ChatColor.RED+" No hay ningún Spawn configurado!");
						return true;
					}
					
				}
				else if(args[0].equalsIgnoreCase("setspawn")) {
					Location l = jugador.getLocation();
					double x = l.getX();
					double y = l.getY();
					double z = l.getZ();
					String world = l.getWorld().getName();
					float yaw = l.getYaw();
					float pitch = l.getPitch();
					FileConfiguration config = plugin.getConfig();
					config.set("Config.Spawn.x", x);
					config.set("Config.Spawn.y", y);
					config.set("Config.Spawn.z", z);
					config.set("Config.Spawn.world", world);
					config.set("Config.Spawn.yaw", yaw);
					config.set("Config.Spawn.pitch", pitch);
					jugador.sendMessage(plugin.nombre+ChatColor.GREEN+" El spawn del servidor ha sido configurado correctamente");
					plugin.saveConfig();
				}
				else if(args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					jugador.sendMessage(plugin.nombre+ChatColor.GREEN+" El plugin se ha recargado correctamente!");
					return true;
				}
				else if(args[0].equalsIgnoreCase("kills")) {
					FileConfiguration config = plugin.getConfig();
					if(!config.contains("Players")) {
						jugador.sendMessage(ChatColor.RED+"----------KILLS----------");
						jugador.sendMessage(ChatColor.GREEN+"Zombies Asesinados: "+ChatColor.GRAY+"Ninguno");
						return true;
					}else {
						if(!config.contains("Players."+jugador.getUniqueId()+".zombiekills")) {
							int cantidadzombies = Integer.valueOf(config.getString("Players."+jugador.getUniqueId()+".zombiekills"));
							jugador.sendMessage(ChatColor.RED+"----------KILLS----------");
							jugador.sendMessage(ChatColor.GREEN+"Zombies Asesinados: "+cantidadzombies);
						}else {
							return true;
						}
					}
					
				}
				else if(args[0].equalsIgnoreCase("report")) {
					
					if(args.length == 1) {
						jugador.sendMessage(plugin.nombre+ChatColor.RED+" ERROR"+ChatColor.GRAY+" Usaste mal el comando!"+ChatColor.YELLOW+" /joacomp report <usuario>");
						return true;
					}else {
						String usuario = args[1];
						if(Bukkit.getPlayer(usuario) != null) {
							FileConfiguration config = plugin.getConfig();
							if(config.contains("Config.usuarios-reportados")){
								List<String> reportados = config.getStringList("Config.usuarios-reportados");
								if(reportados.contains(usuario)) {
									jugador.sendMessage(plugin.nombre+ChatColor.RED+" Ese usuario ya fué reportado!");
									return true;
								}
								reportados.add(usuario);
								config.set("Config.usuarios-reportados", reportados);
								plugin.saveConfig();
								jugador.sendMessage(plugin.nombre+ChatColor.GREEN+" El usuario ha sido reportado exitosamente!");
								return true;
							}else {
								List<String> reportados = new ArrayList<String>();
								reportados.add(usuario);
								config.set("Config.usuarios-reportados", reportados);
								plugin.saveConfig();
								jugador.sendMessage(plugin.nombre+ChatColor.GREEN+" El usuario ha sido reportado exitosamente!");
								return true;
								
								}
						}else {
							jugador.sendMessage(plugin.nombre+ChatColor.RED+" No podés reportar a ese usuario porque no está conectado!");
							return true;
						}
						
						
						
					}
					
				}
				else {
					jugador.sendMessage(plugin.nombre+ChatColor.RED+" Ese comando no existe!");
					return true;
				}
			}else{
				jugador.sendMessage(plugin.nombre+ChatColor.WHITE+" usa /joacomp version para ver la version actual del plugin");
				return true;
			}
		}
		return false;
	}
}
