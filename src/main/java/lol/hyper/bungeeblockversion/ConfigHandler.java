package lol.hyper.bungeeblockversion;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ConfigHandler {

    public static Configuration configuration;
    public static List<Integer> versions;

    public static void loadConfig() {
        File configFile = new File("plugins" + File.separator + "BungeeBlockVersion", "config.yml");
        if (!configFile.exists()) {
            InputStream is = BungeeBlockVersion.getInstance().getResourceAsStream("config.yml");
            try {
                File path = new File("plugins" + File.separator + "BungeeBlockVersion");
                if (path.mkdir()) {
                    Files.copy(is, configFile.toPath());
                    BungeeBlockVersion.getInstance().getProxy().getLogger().info("[BungeeBlockVersion] Copying default config...");
                } else {
                    BungeeBlockVersion.getInstance().getProxy().getLogger().warning("[BungeeBlockVersion] Unable to create config folder!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            versions = configuration.getIntList("versions");
            if (versions.size() == 0) {
                BungeeBlockVersion.getInstance().getProxy().getLogger().warning("[BungeeBlockVersion] There are no versions listed in the config!");
            } else {
                BungeeBlockVersion.getInstance().getProxy().getLogger().info("[BungeeBlockVersion] Loaded " + versions.size() + " versions!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            BungeeBlockVersion.getInstance().getProxy().getLogger().severe("[BungeeBlockVersion] Unable to load configuration file!");
        }
    }
}
