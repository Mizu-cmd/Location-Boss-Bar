package fr.mizu.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.mizu.BossBarDisplayer;
import fr.mizu.LocationBossBar;
import fr.mizu.utils.AreaChecker;

public class DeleteCommand {
    public static void deleteAreaWithID(String id){
        LocationBossBar.getInstance().getAreaConfiguration().set(id+"", null);
        LocationBossBar.getInstance().saveAreaConfig();
        LocationBossBar.getInstance().reloadConfig();
        Bukkit.getOnlinePlayers().forEach(player -> BossBarDisplayer.removePlayerFromBossBar(player, id));
        //BossBarDisplayer.createBossBar();
    }

    public static void deleteCurrent(Player player){
        for (String keys : LocationBossBar.getInstance().getAreaConfiguration().getKeys(false)){
            Location pos1 = LocationBossBar.getInstance().getAreaConfiguration().getLocation(keys+".pos1");
            Location pos2 = LocationBossBar.getInstance().getAreaConfiguration().getLocation(keys+".pos2");
            if (AreaChecker.isInRect(player, pos1, pos2)){
                deleteAreaWithID(keys);
                player.sendMessage(LocationBossBar.PREFIX+"Successfully deleted current area");
            } else {
                player.sendMessage(LocationBossBar.PREFIX+"You are not inside an area");
            }
        }
    }
}
