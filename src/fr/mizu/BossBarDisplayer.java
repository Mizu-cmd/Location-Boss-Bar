package fr.mizu;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarDisplayer {
    private static HashMap<String,BossBar> Bossbars = new HashMap<>();

    public static void createBossBar(){
        for (String keys : LocationBossBar.getInstance().getAreaConfiguration().getKeys(false)){
            BossBar bar = Bukkit.createBossBar(LocationBossBar.getInstance().getAreaConfiguration().getString(keys+".display-name"), BarColor.valueOf(LocationBossBar.getInstance().getAreaConfiguration().getString(keys+".color")), BarStyle.SOLID);
            Bossbars.putIfAbsent(keys, bar);
        }
    }

    public static void addBossBar(BossBar bar, String id){
        Bossbars.put(id, bar);
    }

    public static void removePlayerFromBossBar(Player player, String barID){
        if(Bossbars.get(barID).getPlayers().contains(player)) Bossbars.get(barID).removePlayer(player);
    }

    public static void addPlayerToBossBar(Player player, String barID){
        if(!Bossbars.get(barID).getPlayers().contains(player) && !Bossbars.get(barID).getPlayers().contains(player)) Bossbars.get(barID).addPlayer(player);
    }
}
