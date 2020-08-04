package joaco.joacomagicplugin.eventos;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class Entrar implements Listener{
private JoacoMagicPlugin plugin;
	
	public Entrar(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void alEntrar(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		FileConfiguration config = plugin.getConfig();
		if(config.contains("Config.Spawn.x")) {
			double x = Double.valueOf(config.getString("Config.Spawn.x"));
			double y = Double.valueOf(config.getString("Config.Spawn.y"));
			double z = Double.valueOf(config.getString("Config.Spawn.z"));
			float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
			float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
			World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
			Location l = new Location(world, x, y, z, yaw, pitch);
			jugador.teleport(l);
		}else {
			return;
		}
		String path  = "Config.mensaje-bienvenida";
		if(config.getString(path).equals("true")) {
			FileConfiguration messages = plugin.getMessages();
		List<String> mensaje = messages.getStringList("Messages.mensaje-bienvenida-texto");
		for(int i=0;i<mensaje.size();i++) {
			String texto = mensaje.get(i);
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
		}
		}
	return;
	}
}
