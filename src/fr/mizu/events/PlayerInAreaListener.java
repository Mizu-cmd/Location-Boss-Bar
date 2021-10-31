package fr.mizu.events;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.mizu.BossBarDisplayer;
import fr.mizu.LocationBossBar;
import fr.mizu.utils.AreaChecker;

public class PlayerInAreaListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        for (String keys : LocationBossBar.getInstance().getAreaConfiguration().getKeys(false)){
            Location pos1 = LocationBossBar.getInstance().getAreaConfiguration().getLocation(keys+".pos1");
            Location pos2 = LocationBossBar.getInstance().getAreaConfiguration().getLocation(keys+".pos2");
            if (AreaChecker.isInRect(player, pos1, pos2)){
                if (player.hasPermission("lbb.see")) BossBarDisplayer.addPlayerToBossBar(player, keys);
            } else {
                if (player.hasPermission("lbb.see")) BossBarDisplayer.removePlayerFromBossBar(player, keys);
            }
        }
    }
}
