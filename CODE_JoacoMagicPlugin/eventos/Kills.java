package joaco.joacomagicplugin.eventos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class Kills  implements Listener{
	private JoacoMagicPlugin plugin;
	
	public Kills(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void matarZombies(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		EntityType entidad = event.getEntityType();
	if(killer !=null && killer.getType().equals(EntityType.PLAYER) && entidad.equals(EntityType.ZOMBIE)) {
		FileConfiguration config = plugin.getConfig();
		config.set("Players."+killer.getUniqueId()+".name", killer.getName());
		if(config.contains("Players."+killer.getUniqueId()+".zombiekills")) {
			int cantidadzombies = Integer.valueOf(config.getString("Players."+killer.getUniqueId()+".zombiekills"));
			config.set("Players."+killer.getUniqueId()+".zombiekills", cantidadzombies+1);
			plugin.saveConfig();
			return;
	}else {
		config.set("Players."+killer.getUniqueId()+".zombiekills", 1);
		plugin.saveConfig();
		return;
	}
		
	}
}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void obtenerGemas(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		EntityType entidad = event.getEntityType();
	if(killer !=null && killer.getType().equals(EntityType.PLAYER) && entidad.equals(EntityType.PLAYER)) {
		ItemStack stack = new ItemStack(388, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lGema de la Muerte"));
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&m                                                       "));
		lore.add(ChatColor.translateAlternateColorCodes('&', ""));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&6Se te ha otorgado este artefacto por matar a otro jugador"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&2Esta gema contiene el alma del ser al que mataste"));
		lore.add(ChatColor.translateAlternateColorCodes('&', ""));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&m                                                       "));
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
		stack.setItemMeta(meta);
		if(killer.getInventory().firstEmpty() == -1) {
			killer.sendMessage(ChatColor.DARK_RED+" Tu inventario esta lleno! No recibiste el trofeo");
			return;
		}else {
			killer.getInventory().addItem(stack);
			return;
		}
	}
}
}

