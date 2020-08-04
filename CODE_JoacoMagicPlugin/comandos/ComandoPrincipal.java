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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class ComandoPrincipal implements CommandExecutor {
private JoacoMagicPlugin plugin;
	
	public ComandoPrincipal(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
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
						int id = Integer.valueOf(config.getString("Config.item-requerido.id"));
						int amount = Integer.valueOf(config.getString("Config.item-requerido.amount"));
						ItemStack item = new ItemStack(id, amount);
						ItemMeta meta = item.getItemMeta();
						String pathname = "Config.item-requerido.name";
						String name = "";
						if(config.contains(pathname)) {
							name = ChatColor.translateAlternateColorCodes('&', config.getString(pathname));
							meta.setDisplayName(name);
						}
						String pathlore= "Config.item-requerido.lore";
						List<String> lore = new ArrayList<String>();
						if(config.contains(pathlore)) {
							lore = config.getStringList(pathlore);
							for(int i=0;i<lore.size();i++) {
								lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
							}
							meta.setLore(lore);
						}
						String pathenchants= "Config.item-requerido.enchants";
						List<String> enchants = new ArrayList<String>();
						if(config.contains(pathenchants)) {
							enchants = config.getStringList(pathenchants);
							for(int i=0;i<lore.size();i++) {
								String[] separacion = new String[2];
								separacion = enchants.get(i).split(";");
								int level = Integer.valueOf(separacion[1]);
								meta.addEnchant(Enchantment.getByName(separacion[0]), level, true);
							}
						}
						
						
						item.setItemMeta(meta);
						
						ItemStack[] inventory = jugador.getInventory().getContents();
						for(int i=0;i<inventory.length;i++){
							if(inventory[i] != null && inventory[i].isSimilar(item) && inventory[i].getAmount() >= item.getAmount()) {
								double x = Double.valueOf(config.getString("Config.Spawn.x"));
								double y = Double.valueOf(config.getString("Config.Spawn.y"));
								double z = Double.valueOf(config.getString("Config.Spawn.z"));
								float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
								float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
								World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
								Location l = new Location(world, x, y, z, yaw, pitch);    //252 64 260 -90 0
								jugador.teleport(l);
								return true;
							}
						}
						
						
						
						/*ItemStack[] inventory = jugador.getInventory().getContents();
						for(int i=0;i<inventory.length;i++){
						if(inventory[i] != null && inventory[i].hasItemMeta() && inventory[i].getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT)) {
							if(inventory[i].getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 2) {
								if(inventory[i].getTypeId() == 264) {
									double x = Double.valueOf(config.getString("Config.Spawn.x"));
									double y = Double.valueOf(config.getString("Config.Spawn.y"));
									double z = Double.valueOf(config.getString("Config.Spawn.z"));
									float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
									float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
									World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
									Location l = new Location(world, x, y, z, yaw, pitch);    //252 64 260 -90 0
									jugador.teleport(l);
									return true;
								}
								
							}
						}*/
							
						jugador.sendMessage(plugin.nombre+ChatColor.RED+" No tenes el item necesario");
						return true;
					}
						else {
							jugador.sendMessage(plugin.nombre+ChatColor.RED+" No tenes el item necesario");
							return true;
						}
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
					plugin.reloadMessages();
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
			
			}
		
		return false;
	}
}
