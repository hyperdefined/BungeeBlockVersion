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
