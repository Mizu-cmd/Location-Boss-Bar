package fr.mizu.commands;

import org.bukkit.entity.Player;

import fr.mizu.LocationBossBar;

public class ListCommand {
    public static void getAreaList(Player player){
        for (String keys : LocationBossBar.getInstance().getAreaConfiguration().getKeys(false)){
            String text = LocationBossBar.getInstance().getAreaConfiguration().getString(keys+".display-name");
            player.sendMessage(LocationBossBar.PREFIX+"[§7ID:"+keys+"]§6 "+text);
        }
    }
}
