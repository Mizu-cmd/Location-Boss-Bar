package fr.mizu.commands;

import java.util.Arrays;
import java.util.List;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.mizu.LocationBossBar;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, Command cmd, String arg2, String[] args) {
        Player sender = (Player) arg0;

        if (args.length > 0 ){
            if(args[0].equalsIgnoreCase("pos1") && sender.hasPermission("lbb.pos")){
                this.addPos1(sender, sender.getLocation());
                sender.sendMessage(LocationBossBar.PREFIX+"Pos 1 successfully placed");
                return false;
            }
            if(args[0].equalsIgnoreCase("pos2") && sender.hasPermission("lbb.pos")){
                this.addPos2(sender, sender.getLocation());
                sender.sendMessage(LocationBossBar.PREFIX+"Pos 2 successfully placed");
                return false;
            }

            if(args[0].equalsIgnoreCase("help") && sender.hasPermission("lbb.help")){
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb pos1 - Save the area pos 1 at your current location");
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb pos2 - Save the area pos 2 at your current location");
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb create <name ID> <'displayed text'> <color> - Create an area with the previously setted position <name ID> is used to rocognize the area");
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb delete <name ID/current> - Delete an area");
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb list - Saw all the created area");
                sender.sendMessage(LocationBossBar.PREFIX+"§6/lbb reload - Reload the plugin");
                return false;
            }

            if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("lbb.reload")){
                LocationBossBar.getInstance().reloadConfig();
                Plugin plugin = Bukkit.getPluginManager().getPlugin("LocationBossBar");
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getPluginManager().enablePlugin(plugin);
            }

            if(args[0].equalsIgnoreCase("create") && sender.hasPermission("lbb.create")){
                if (args.length < 3) {
                    sender.sendMessage(LocationBossBar.PREFIX+"Command usage : /lbb create <display text> <color>");
                    return false;
                }

                if (LocationBossBar.getInstance().getWorldEdit() != null && !(Area.area.containsKey(sender))){
                    BukkitPlayer bPlayer = BukkitAdapter.adapt(sender);
                    try {
                        Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());

                        BlockVector3 vector1 = region.getMinimumPoint();
                        Location pos1 = new Location(sender.getLocation().getWorld(), vector1.getBlockX(), vector1.getBlockY(), vector1.getBlockZ());

                        BlockVector3 vector2 = region.getMaximumPoint();
                        Location pos2 = new Location(sender.getLocation().getWorld(), vector2.getBlockX(), vector2.getBlockY(), vector2.getBlockZ());

                        List<BarColor> colors = Arrays.asList(BarColor.values());
                        if (!colors.toString().contains(args[args.length-1].toUpperCase())){
                            sender.sendMessage(LocationBossBar.PREFIX+"Invalid color : §c"+args[args.length-1]);
                            return false;
                        }
                        String s = "";

                        for(int i = 1; i < args.length-1; i++){
                            String arg = args[i] + " ";
                            s = s + arg;
                        }

                        s = s.substring(s.indexOf( ' ' )).replace("'", "");
                        System.out.println(s);

                        CreateCommand.createArea(args[1] ,pos1, pos2, s.replace("*", "§"), args[args.length-1].toUpperCase());
                        Area.area.remove(sender);
                        sender.sendMessage(LocationBossBar.PREFIX+"§aSucessfully created area with text: §6"+s.replace("*", "§"));
                        return false;

                    } catch (IncompleteRegionException e) {
                        sender.sendMessage(LocationBossBar.PREFIX+"§cPlease use a complete world edit selection or §6/lbb pos");
                        return false;
                    }
                }
    
                    if (Area.area.containsKey(sender) && Area.area.get(sender).getPos1() != null && Area.area.get(sender).getPos2() != null){
    
                        List<BarColor> colors = Arrays.asList(BarColor.values());
                        if (!colors.toString().contains(args[args.length-1].toUpperCase())){
                            sender.sendMessage(LocationBossBar.PREFIX+"Invalid color : §c"+args[args.length-1]);
                            return false;
                        }

                        String s = args[2].substring(args[2].indexOf( ' ' ) + 1);  
    
                        CreateCommand.createArea(args[1] ,Area.area.get(sender).getPos1(), Area.area.get(sender).getPos2(), s.replace("*", "§"), args[args.length-1].toUpperCase());
                        Area.area.remove(sender);
                        sender.sendMessage(LocationBossBar.PREFIX+"§aSucessfully created area with text: §6"+s.replace("*", "§"));
                    } else {
                        sender.sendMessage(LocationBossBar.PREFIX+"You must set the area location using /lbb pos");
                }
            }

            if (args[0].equalsIgnoreCase("delete") && sender.hasPermission("lbb.delete")){
                if (args.length < 2){
                    sender.sendMessage(LocationBossBar.PREFIX+"Command usage : /lbb delete <id/current>");
                    return false;
                }
                    if (args[1].equalsIgnoreCase("current")){
                        DeleteCommand.deleteCurrent(sender);
                        return false;
                    } else if (LocationBossBar.getInstance().getAreaConfiguration().getKeys(false).contains(args[1])){
                        DeleteCommand.deleteAreaWithID(args[1]);
                        sender.sendMessage(LocationBossBar.PREFIX+"Sucessfully deleted area id §6"+args[1]);
                    } else {
                        sender.sendMessage(LocationBossBar.PREFIX+"This area doesn't exist");
                        return false;
                    }
            }
            if(args[0].equalsIgnoreCase("list") && sender.hasPermission("lbb.list")){
                ListCommand.getAreaList(sender);
            }

        }
        return false;
    }

    private void addPos1(Player player, Location loc){
        if (Area.area.containsKey(player)){
            Area.area.get(player).setPos1(loc);
        } else {
            Area.area.put(player, new Area());
            Area.area.get(player).setPos1(loc);
        }
    }

    private void addPos2(Player player, Location loc){
        if (Area.area.containsKey(player)){
            Area.area.get(player).setPos2(loc);
        } else {
            Area.area.put(player, new Area());
            Area.area.get(player).setPos2(loc);
        }
    }

}
