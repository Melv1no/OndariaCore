package fr.ondaria.core.listener;

import fr.ondaria.core.OndariaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogListener implements Listener {

    private OndariaCore instance = OndariaCore.getInstance();

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e){
        Player player = (Player) instance.getServer().getOfflinePlayer(e.getPlayerProfile().getId());
        if(instance.getMaintenanceManager().isInMaintenance && !player.hasPermission("maintenance.permission_bypass")){
            e.setKickMessage(instance.getConfStr("maintenance.kick_message"));
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(instance.getConfStr("maintenance.kick_message"));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if(!player.hasPlayedBefore()){instance.getWelcomeManager().lastPlayer = player;}else{e.setJoinMessage(instance.getConfStr("welcome.first_join_message").replace("{player}", player.getDisplayName()));}

        if(player.hasPermission(instance.getConfStr("login_message.silent_join"))){e.setJoinMessage(""); return;}
        e.setJoinMessage(instance.getConfStr("login_message.joining_message").replace("{player}", player.getDisplayName()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();

        e.setQuitMessage(instance.getConfStr("login_message.leaving_message").replace("{player}", player.getDisplayName()));
    }

}
