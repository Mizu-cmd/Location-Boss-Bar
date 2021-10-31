package fr.mizu.commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Area {

    public static HashMap<Player, Area> area = new HashMap<>();

    private Location pos1;
    private Location pos2;
    
    public Location getPos1() {
        return pos1;
    }
    
    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
    
    public boolean isComplete(){
        if (pos1 != null && pos2 != null){
            return true;
        }
        return false;
    }
    
}
