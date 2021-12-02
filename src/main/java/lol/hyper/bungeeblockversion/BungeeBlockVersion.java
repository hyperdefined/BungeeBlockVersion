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
import lol.hyper.bungeeblockversion.tools.VersionToStrings;
import lol.hyper.githubreleaseapi.GitHubRelease;
import lol.hyper.githubreleaseapi.GitHubReleaseAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.bstats.bungeecord.Metrics;

import java.io.IOException;
import java.util.logging.Logger;

public final class BungeeBlockVersion extends Plugin implements Listener {

    public final Logger logger = this.getLogger();

    public ConfigHandler configHandler;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler(this);
        VersionToStrings.init();
        configHandler.loadConfig();
        getProxy().getPluginManager().registerCommand(this, new CommandReload("bbvreload", configHandler));
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);

        new Metrics(this, 9392);
        ProxyServer.getInstance().getScheduler().runAsync(this, this::checkForUpdates);
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

    public void checkForUpdates() {
        GitHubReleaseAPI api;
        try {
            api = new GitHubReleaseAPI("BungeeBlockVersion", "hyperdefined");
        } catch (IOException e) {
            logger.warning("Unable to check updates!");
            e.printStackTrace();
            return;
        }
        GitHubRelease current = api.getReleaseByTag(this.getDescription().getVersion());
        GitHubRelease latest = api.getLatestVersion();
        if (current == null) {
            logger.warning("You are running a version that does not exist on GitHub. If you are in a dev environment, you can ignore this. Otherwise, this is a bug!");
            return;
        }
        int buildsBehind = api.getBuildsBehind(current);
        if (buildsBehind == 0) {
            logger.info("You are running the latest version.");
        } else {
            logger.warning("A new version is available (" + latest.getTagVersion() + ")! You are running version " + current.getTagVersion() + ". You are " + buildsBehind + " version(s) behind.");
        }
    }
}
