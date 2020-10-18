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
    public static final Integer CONFIG_VERSION = 1;

    public static void loadConfig() {
        File configFile = new File("plugins" + File.separator + "BungeeBlockVersion", "config.yml");
        if (!configFile.exists()) {
            InputStream is = BungeeBlockVersion.getInstance().getResourceAsStream("config.yml");
            try {
                File path = new File("plugins" + File.separator + "BungeeBlockVersion");
                if (path.mkdir()) {
                    Files.copy(is, configFile.toPath());
                    BungeeBlockVersion.getInstance().logger.info("Copying default config...");
                } else {
                    BungeeBlockVersion.getInstance().logger.warning("Unable to create config folder!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            versions = configuration.getIntList("versions");
            if (configuration.getInt("config-version") != CONFIG_VERSION) {
                BungeeBlockVersion.getInstance().logger.warning("Your config is outdated! Please delete config.yml and regen it. Some features may not work.");
            }
            if (versions.size() == 0) {
                BungeeBlockVersion.getInstance().logger.warning("There are no versions listed in the config!");
            } else {
                BungeeBlockVersion.getInstance().logger.info("Loaded " + versions.size() + " versions!");
            }
            for (Integer i : versions) {
                if (!VersionToStrings.versionStrings.containsKey(i)) {
                    BungeeBlockVersion.getInstance().logger.warning("Version " + i + " is NOT a valid version number!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            BungeeBlockVersion.getInstance().logger.severe("Unable to load configuration file!");
        }
    }
}
