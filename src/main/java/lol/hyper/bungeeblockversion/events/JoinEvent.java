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
import lol.hyper.bungeeblockversion.tools.ConfigHandler;
import lol.hyper.bungeeblockversion.tools.VersionToStrings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

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

        if (ConfigHandler.versions.contains(event.getConnection().getVersion())) {
            event.setCancelled(true);
            String blockedMessage = ConfigHandler.configuration.getString("disconnect-message");
            if (blockedMessage.contains("{VERSIONS}")) {
                blockedMessage = blockedMessage.replace("{VERSIONS}", VersionToStrings.allowedVersions(ConfigHandler.versions));
            }
            Component blockedMessageComponent = bungeeBlockVersion.miniMessage.deserialize(blockedMessage);
            BaseComponent blockedMessageBaseComponent = new TextComponent(BungeeComponentSerializer.get().serialize(blockedMessageComponent));
            event.setCancelReason(blockedMessageBaseComponent);
            bungeeBlockVersion.logger.info("Blocking player " + event.getConnection().getName() + " because they are playing on version "
                    + VersionToStrings.versionStrings.get(event.getConnection().getVersion()) + " which is blocked!");
        }
    }
}
