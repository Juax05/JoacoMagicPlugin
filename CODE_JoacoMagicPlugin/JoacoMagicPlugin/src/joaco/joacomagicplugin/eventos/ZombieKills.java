package joaco.joacomagicplugin.eventos;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class ZombieKills  implements Listener{
	private JoacoMagicPlugin plugin;
	
	public ZombieKills(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void matarZombies(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		EntityType entidad = event.getEntityType();
		if(killer !=null && killer.getType().equals(EntityType.PLAYER) && entidad.equals(EntityType.ZOMBIE)) {
		FileConfiguration config = plugin.getConfig();
		int cantidadzombies = Integer.valueOf(config.getString("Players."+killer.getUniqueId()+".zombiekills"));
		config.set("Players."+killer.getUniqueId()+".zombiekills", cantidadzombies+1);
		plugin.saveConfig();
		return;
	}else {
		FileConfiguration config = plugin.getConfig();
		config.set("Players."+killer.getUniqueId()+".zombiekills", 1);
		plugin.saveConfig();
		return;
	}
		
	}
}

