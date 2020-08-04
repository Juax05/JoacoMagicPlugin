package joaco.joacomagicplugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import joaco.joacomagicplugin.comandos.ComandoPrincipal;
import joaco.joacomagicplugin.comandos.ComandoYoutube;
import joaco.joacomagicplugin.eventos.Entrar;
import joaco.joacomagicplugin.eventos.ZombieKills;

public class JoacoMagicPlugin extends JavaPlugin{
	public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ChatColor.GREEN+"["+pdffile.getName()+"]";
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" El plugin se activó exitosamente(version:"+ChatColor.RED+version+ChatColor.WHITE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.BLUE+"Gracias por utilizar este plugin! :)");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		registrarComandos();
		registerEvents();
		registerConfig();
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" El plugin se desactivó exitosamente(version:"+ChatColor.RED+version+ChatColor.WHITE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.BLUE+"Gracias por utilizar este plugin! :)");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
	}
	
	public void registrarComandos() {
		this.getCommand("youtube").setExecutor(new ComandoYoutube(this));
		this.getCommand("joacomp").setExecutor(new ComandoPrincipal(this));
	}
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Entrar(this), this);
		pm.registerEvents(new ZombieKills(this), this);
	}
	
	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
}
