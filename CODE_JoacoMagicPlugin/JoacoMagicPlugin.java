package joaco.joacomagicplugin;



import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import joaco.joacomagicplugin.comandos.ComandoPrincipal;
import joaco.joacomagicplugin.comandos.ComandoYoutube;
import joaco.joacomagicplugin.eventos.Entrar;
import joaco.joacomagicplugin.eventos.Kills;

public class JoacoMagicPlugin extends JavaPlugin{
	private FileConfiguration messages = null;
	private File messagesFile = null;
	public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ChatColor.GREEN+"["+pdffile.getName()+"]";
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" El plugin se activó exitosamente(version:"+ChatColor.RED+version+ChatColor.WHITE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.BLUE+"Gracias por utilizar este plugin! :)");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		registerCommands();
		registerEvents();
		registerConfig();
		registerMessages();
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" El plugin se desactivó exitosamente(version:"+ChatColor.RED+version+ChatColor.WHITE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.BLUE+"Gracias por utilizar este plugin! :)");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"<------------------------------>");
	}
	
	public void registerCommands() {
		this.getCommand("youtube").setExecutor(new ComandoYoutube(this));
		this.getCommand("joacomp").setExecutor(new ComandoPrincipal(this));
	}
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Entrar(this), this);
		pm.registerEvents(new Kills(this), this);
	}
	
	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}



public FileConfiguration getMessages() {
	if(messages == null) {
		reloadMessages();
	}
	return messages;
}

public void reloadMessages(){
	if(messages == null){
		messagesFile = new File(getDataFolder(),"messages.yml");
	}
	messages = YamlConfiguration.loadConfiguration(messagesFile);
	Reader defConfigStream;
	try{
		defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
		if(defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			messages.setDefaults(defConfig);
		}			
	}catch(UnsupportedEncodingException e){
		e.printStackTrace();
	}
}

public void saveMessages(){
	try{
		messages.save(messagesFile);			
	}catch(IOException e){
		e.printStackTrace();
	}
}

public void registerMessages(){
	messagesFile = new File(this.getDataFolder(),"messages.yml");
	if(!messagesFile.exists()){
		this.getMessages().options().copyDefaults(true);
		saveMessages();
	}
}
}


















