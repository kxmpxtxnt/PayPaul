package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Help extends AdvancedCommand {

  public Help(Plugin plugin) {
    super(plugin);
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Just an help!"));
  }
}
