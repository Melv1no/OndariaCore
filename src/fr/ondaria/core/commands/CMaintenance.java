package fr.ondaria.core.commands;

import fr.ondaria.core.OndariaCore;
import fr.ondaria.core.manager.MaintenanceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMaintenance implements CommandExecutor {

    private OndariaCore instance = OndariaCore.getInstance();
    private int timeleft = 0;
    private List<Integer> time_interval = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission(instance.getConfStr("maintenance.permission"))){return false;}

        if(args.length == 2 && args[0].equals("start") && args[1].equals("now")){
            instance.getMaintenanceManager().isInMaintenance = true;
            sender.sendMessage(instance.getConfStr("maintenance.on"));
            for(Player player : instance.getServer().getOnlinePlayers() ){
                if(!player.hasPermission("maintenance.permission_bypass")){
                    player.kickPlayer(instance.getConfStr("maintenance.kick_message"));
                }
            }
        }
        if(args.length == 1 && args[0].equals("stop")){instance.getMaintenanceManager().isInMaintenance = false; sender.sendMessage(instance.getConfStr("maintenance.off"));}

        try{
            timeleft = instance.getConfig().getInt("maintenance.time");
            time_interval = instance.getConfig().getIntegerList("maintenance.interval");

        }catch (Exception e){
            sender.sendMessage("§cUne erreur c'est produite, vérifier la configuration");
        }
        instance.getMaintenanceManager().isInMaintenance = true;
        instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
            @Override
            public void run() {
                timeleft--;
                if(time_interval.contains(timeleft)){
                    instance.getServer().broadcastMessage(instance.getConfStr("maintenance.maintenance_message").replace("{time_left}", String.valueOf(timeleft)));

                }
                if(timeleft == 0){
                    for(Player player : instance.getServer().getOnlinePlayers() ){
                        if(!player.hasPermission("maintenance.permission_bypass")){
                            player.kickPlayer(instance.getConfStr("maintenance.kick_message"));
                        }
                    }
                    return;
                }
            }
        }, 20);



        return false;
    }
}
