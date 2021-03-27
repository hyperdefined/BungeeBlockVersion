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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {

    private ConfigHandler configHandler;
    public CommandReload(String name, ConfigHandler configHandler) {
        super(name);
        this.configHandler = configHandler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bbv.reload")) {
            configHandler.loadConfig();
            sender.sendMessage(new TextComponent(ChatColor.GREEN + "Config reloaded!"));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to reload!"));
        }
    }
}