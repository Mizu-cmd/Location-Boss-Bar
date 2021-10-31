package fr.mizu.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.mizu.LocationBossBar;

public class LBBTabCompleter implements TabCompleter   {

    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
        if (!arg0.hasPermission("lbb.create")) return null;

        if (args[0].equalsIgnoreCase("create") && args.length > 2) {
            if (args[args.length-1].length() == 0){
                List<String> list = new ArrayList<>();
                for (BarColor color : BarColor.values()){
                    list.add(color.toString());
                }
                return list;
            }
            List<String> list = new ArrayList<>();
            for (int i = 0; i < BarColor.values().length; i++){
                if (BarColor.values()[i].toString().startsWith(args[args.length-1].toUpperCase())){
                    list.add(BarColor.values()[i].toString());
                }
            }
            return list;
        }
        if(args[0].equalsIgnoreCase("delete")){
            List<String> list = new ArrayList<>();
            if (args.length < 2) return null;
            if (args[1].length()==0) list.add("current");
            for (String keys : LocationBossBar.getInstance().getAreaConfiguration().getKeys(false)){
                if (keys.startsWith(args[args.length-1])){
                    list.add(keys);
                }
            }
            return list;
        }
        if (args.length < 2) return Arrays.asList("pos1", "pos2", "create", "delete", "list", "help", "reload");

        return null;
    }
    
}
