package fr.mizu.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import fr.mizu.BossBarDisplayer;
import fr.mizu.LocationBossBar;

public class CreateCommand {

    public static void createArea(String id,Location pos1, Location pos2, String displayName, String color){
        LocationBossBar.getInstance().getAreaConfiguration().set(id+".pos1", pos1);
        LocationBossBar.getInstance().getAreaConfiguration().set(id+".pos2", pos2);
        LocationBossBar.getInstance().getAreaConfiguration().set(id+".display-name", displayName);
        LocationBossBar.getInstance().getAreaConfiguration().set(id+".color", color);
        LocationBossBar.getInstance().saveAreaConfig();
        LocationBossBar.getInstance().reloadConfig();

        BossBar bar = Bukkit.createBossBar(displayName, BarColor.valueOf(color), BarStyle.SOLID);

        BossBarDisplayer.addBossBar(bar, id);
    }
}
