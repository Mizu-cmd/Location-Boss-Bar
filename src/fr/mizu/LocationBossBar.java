package fr.mizu;

import java.io.File;
import java.io.IOException;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mizu.commands.CommandListener;
import fr.mizu.commands.LBBTabCompleter;
import fr.mizu.events.PlayerInAreaListener;
import fr.mizu.utils.UpdateChecker;

public class LocationBossBar extends JavaPlugin {

    public static String PREFIX = "";

    private File area;
    private FileConfiguration areaConfiguration;

    private static LocationBossBar instance;

    @Override
    public void onEnable() {

        instance = this;

        createFile();

        BossBarDisplayer.createBossBar();

        PREFIX = getConfig().getString("prefix");

        new UpdateChecker(this, 12345).getVersion(version -> {
            if (!(this.getDescription().getVersion().equals(version))) Bukkit.broadcastMessage(PREFIX+"There is a new version availlable");
        });

        getCommand("lbb").setExecutor(new CommandListener());
        getCommand("lbb").setTabCompleter(new LBBTabCompleter());
        Bukkit.getPluginManager().registerEvents(new PlayerInAreaListener(), this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    private void createFile() {
        saveDefaultConfig();

        this.area = new File(getDataFolder(), "area.yml");

        if(!area.exists()){
            area.getParentFile().mkdir();
            saveResource("area.yml", false);
        }

        areaConfiguration = new YamlConfiguration();

        try {
            areaConfiguration.load(area);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveAreaConfig(){
        try {
            areaConfiguration.save(area);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getAreaConfiguration() {
        return areaConfiguration;
    }


    public static LocationBossBar getInstance() {
        return instance;
    }

    public WorldEditPlugin getWorldEdit(){
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        if (p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
        else return null;
    }

}
