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

package lol.hyper.bungeeblockversion.events;

import lol.hyper.bungeeblockversion.BungeeBlockVersion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinEvent implements Listener {

    private final BungeeBlockVersion bungeeBlockVersion;

    public JoinEvent(BungeeBlockVersion bungeeBlockVersion) {
        this.bungeeBlockVersion = bungeeBlockVersion;
    }

    @EventHandler
    public void onPreConnect(LoginEvent event) {
        if (event.isCancelled()) {
            return;
        }
        int version = event.getConnection().getVersion();
        String playerName = event.getConnection().getName();
        if (bungeeBlockVersion.configHandler.configuration.getBoolean("log-connection-versions")) {
            bungeeBlockVersion.logger.info("Player is connecting with protocol version: " + version);
        }

        if (!bungeeBlockVersion.configHandler.blockedVersions.contains(version)) {
            return;
        }

        event.setCancelled(true);
        String blockedMessage = bungeeBlockVersion.configHandler.configuration.getString("disconnect-message");
        String allowedVersions = allowedVersions(bungeeBlockVersion.configHandler.blockedVersions);
        if (allowedVersions == null) {
            blockedMessage = "<red>All versions are currently blocked from playing.";
        }
        if (blockedMessage.contains("{VERSIONS}")) {
            blockedMessage = blockedMessage.replace("{VERSIONS}", allowedVersions);
        }
        Component blockedMessageComponent = bungeeBlockVersion.miniMessage.deserialize(blockedMessage);
        BaseComponent blockedMessageBaseComponent = new TextComponent(BungeeComponentSerializer.get().serialize(blockedMessageComponent));
        event.setReason(blockedMessageBaseComponent);
        bungeeBlockVersion.logger.info("Blocking player " + playerName + " because they are playing on version " + bungeeBlockVersion.configHandler.versionMap.get(version) + " which is blocked!");
    }

    /**
     * Builds a string that will show what versions the server supports. Example: 1.8 to 1.14.4
     *
     * @param deniedVersions Versions to deny.
     * @return Returns the string of versions.
     */
    public String allowedVersions(List<Integer> deniedVersions) {
        List<Integer> allVersions = new ArrayList<>(ProtocolConstants.SUPPORTED_VERSION_IDS);
        allVersions.removeAll(deniedVersions);
        if (allVersions.isEmpty()) {
            return null;
        }
        int minVersion = Collections.min(allVersions);
        int maxVersion = Collections.max(allVersions);

        return bungeeBlockVersion.configHandler.versionMap.get(minVersion) + " to " + bungeeBlockVersion.configHandler.versionMap.get(maxVersion);
    }
}