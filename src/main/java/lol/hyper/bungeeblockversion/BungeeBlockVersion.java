package lol.hyper.bungeeblockversion;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Logger;

public final class BungeeBlockVersion extends Plugin implements Listener {

    public Logger logger = this.getLogger();

    public ConfigHandler configHandler;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler(this);
        VersionToStrings.init();
        configHandler.loadConfig();
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CommandReload("bbvreload", configHandler));

        new UpdateChecker(this, 84685).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("You are running the latest version.");
            } else {
                logger.info("There is a new version available! Please download at https://www.spigotmc.org/resources/bungeeblockversion.84685/");
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPreConnect(LoginEvent e) {
        if (e.isCancelled()) {
            return;
        }
        
        if (ConfigHandler.versions.contains(e.getConnection().getVersion())) {
            e.setCancelled(true);
            String allowedVersions = VersionToStrings.versionBuilder(ConfigHandler.versions.toArray(new Integer[0]));
            String blockedMessage = ConfigHandler.configuration.getString("disconnect-message");
            if (blockedMessage.contains("{VERSIONS}")) {
                blockedMessage = blockedMessage.replace("{VERSIONS}", allowedVersions);
            }
            e.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', blockedMessage)));
            logger.info("Blocking player " + e.getConnection().getName() + " because they are playing on version " + VersionToStrings.versionStrings.get(e.getConnection().getVersion()) + " which is blocked!");
        }
    }
}
