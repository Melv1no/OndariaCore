package fr.ondaria.core.commands;

import fr.ondaria.core.OndariaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CWelcome implements CommandExecutor {

    private OndariaCore instance = OndariaCore.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)){ sender.sendMessage("Only for player, sorry babe");return false;}

        Player player = (Player) sender;

        if(instance.getWelcomeManager().lastPlayer == null){/*Y'as pas wsh*/}

        for(Map.Entry<Player,Player> e : instance.getWelcomeManager().getWelcome_sayed().entrySet()){
            if(e.getKey() == player && e.getValue() == instance.getWelcomeManager().lastPlayer){
                sender.sendMessage(instance.getConfStr("welcome.already_say_welcome").replace("{player}",instance.getWelcomeManager().lastPlayer.getDisplayName()));
                return false;
            }
        }

        instance.getWelcomeManager().getWelcome_sayed().put(player,instance.getWelcomeManager().lastPlayer);
        player.chat(instance.getConfStr("welcome.welcome_message").replace("{player}",instance.getWelcomeManager().lastPlayer.getDisplayName()));
        if(instance.getConfig().getBoolean("welcome.reward")){instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), instance.getConfStr("welcome.reward_command"));}

        return false;

    }
}
