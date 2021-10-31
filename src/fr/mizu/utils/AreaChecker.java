package fr.mizu.utils;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AreaChecker {

    public static boolean isInRect(Player player, Location loc1, Location loc2) {
        double[] dim = new double[2];
        
        dim[0] = loc1.getX();
        dim[1] = loc2.getX();
        Arrays.sort(dim);
        if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
        return false;
        
        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);
        if(player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
        return false;
        
        dim[0] = loc1.getY();
        dim[1] = loc2.getY();
        Arrays.sort(dim);
        if(player.getLocation().getY() > dim[1] || player.getLocation().getY() < dim[0])
        return false;
        
        return true;
    }
    
}
