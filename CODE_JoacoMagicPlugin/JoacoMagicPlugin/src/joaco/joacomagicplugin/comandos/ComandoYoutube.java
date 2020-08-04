package joaco.joacomagicplugin.comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import joaco.joacomagicplugin.JoacoMagicPlugin;

public class ComandoYoutube implements CommandExecutor{

	private JoacoMagicPlugin plugin;
	
	public ComandoYoutube(JoacoMagicPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+" No podés ejecutar este comando desde la consola!");
			return false;
		}else {
			Player jugador = (Player) sender;
			jugador.sendMessage(ChatColor.RED+"----------------------------------------------");
			jugador.sendMessage(ChatColor.YELLOW+"Visitá mi canal de Youtube Juaxelcool: "+ChatColor.RED+"https://www.youtube.com/channel/UCuMWZxbVuEJlJYYTFZQ3IIw");
			jugador.sendMessage(ChatColor.RED+"----------------------------------------------");
			return true;
			
			
		}
	}

}
