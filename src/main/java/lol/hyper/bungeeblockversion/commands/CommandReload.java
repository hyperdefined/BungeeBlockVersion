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

package lol.hyper.bungeeblockversion.commands;

import lol.hyper.bungeeblockversion.BungeeBlockVersion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {

    private final BungeeBlockVersion bungeeBlockVersion;

    public CommandReload(String name, BungeeBlockVersion bungeeBlockVersion) {
        super(name);
        this.bungeeBlockVersion = bungeeBlockVersion;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bbv.reload")) {
            bungeeBlockVersion.getAdventure().sender(sender).sendMessage(Component.text("Config reloaded!").color(NamedTextColor.GREEN));
            bungeeBlockVersion.configHandler.loadConfig();
        } else {
            bungeeBlockVersion.getAdventure().sender(sender).sendMessage(Component.text("You do not have permission for this command.").color(NamedTextColor.RED));
        }
    }
}
