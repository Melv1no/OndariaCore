package fr.ondaria.core.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WelcomeManager {

    public WelcomeManager(){
        welcome_sayed = new HashMap<Player,Player>();
        lastPlayer = null;
    }

    private HashMap<Player, Player> welcome_sayed;
    public HashMap<Player,Player> getWelcome_sayed(){return welcome_sayed;}
    public Player lastPlayer;


}
