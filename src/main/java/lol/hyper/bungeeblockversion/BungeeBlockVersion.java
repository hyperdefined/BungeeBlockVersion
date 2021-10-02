/*
 * This file is part of BungeeBlockVersion.
 *
 * BungeeBlockVersion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeBlockVersion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeBlockVersion.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.bungeeblockversion;

import lol.hyper.bungeeblockversion.commands.CommandReload;
import lol.hyper.bungeeblockversion.tools.ConfigHandler;
import lol.hyper.bungeeblockversion.tools.UpdateChecker;
import lol.hyper.bungeeblockversion.tools.VersionToStrings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.bstats.bungeecord.Metrics;

import java.util.Collections;
import java.util.logging.Logger;

public final class BungeeBlockVersion extends Plugin implements Listener {

    public final Logger logger = this.getLogger();

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
                logger.info(
                        "There is a new version available! Please download at https://www.spigotmc.org/resources/bungeeblockversion.84685/");
            }
        });
        Metrics metrics = new Metrics(this, 9392);
    }

    @EventHandler
    public void onPreConnect(LoginEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (ConfigHandler.versions.contains(event.getConnection().getVersion())) {
            event.setCancelled(true);
            String allowedVersions = VersionToStrings.allowedVersions(ConfigHandler.versions);
            String blockedMessage = ConfigHandler.configuration.getString("disconnect-message");
            if (blockedMessage.contains("{VERSIONS}")) {
                blockedMessage = blockedMessage.replace("{VERSIONS}", allowedVersions);
            }
            event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', blockedMessage)));
            logger.info("Blocking player " + event.getConnection().getName() + " because they are playing on version "
                    + VersionToStrings.versionStrings.get(event.getConnection().getVersion()) + " which is blocked!");
        }
    }

    @EventHandler
    public void onServerPing(ProxyPingEvent event) {
        if (!ConfigHandler.configuration.getBoolean("send-versions-if-outdated")) {
            return;
        }

        ServerPing.Protocol protocol = event.getResponse().getVersion();
        if (ConfigHandler.versions.contains(protocol.getProtocol())) {
            protocol.setProtocol(Collections.min(ConfigHandler.versions));
        }
        protocol.setName(VersionToStrings.allowedVersions(ConfigHandler.versions));
    }
}
