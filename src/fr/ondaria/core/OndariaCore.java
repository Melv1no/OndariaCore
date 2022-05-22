package fr.ondaria.core;

import fr.ondaria.core.commands.CMaintenance;
import fr.ondaria.core.commands.CWelcome;
import fr.ondaria.core.listener.PlayerLogListener;
import fr.ondaria.core.manager.MaintenanceManager;
import fr.ondaria.core.manager.WelcomeManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class OndariaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        log = Logger.getLogger("Minecraft");
        instance = this;
        saveDefaultConfig();

        if(getConfig().getBoolean("login_message.enable")){getServer().getPluginManager().registerEvents(new PlayerLogListener(), this);}

        if(getConfig().getBoolean("welcome.enable")){getServer().getPluginCommand("bvn").setExecutor(new CWelcome());}
        welcomeManager = new WelcomeManager();

        if(getConfig().getBoolean("maintenance.enable")){getServer().getPluginCommand("maintenance").setExecutor(new CMaintenance());}
        maintenanceManager = new MaintenanceManager();


    }

    @Override
    public void onDisable() {

    }


    public MaintenanceManager getMaintenanceManager(){return maintenanceManager;}
    private MaintenanceManager maintenanceManager;
    private WelcomeManager welcomeManager;
    public WelcomeManager getWelcomeManager(){return welcomeManager;}
    public Logger log;
    public static OndariaCore getInstance(){return instance;}
    private static OndariaCore instance;
    public String getConfStr(String confPath){return getConfig().getString(confPath).replace("&","§");}
}
