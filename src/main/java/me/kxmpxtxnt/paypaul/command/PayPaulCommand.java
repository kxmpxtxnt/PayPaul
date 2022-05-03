package me.kxmpxtxnt.paypaul.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.command.paypaul.Help;
import me.kxmpxtxnt.paypaul.command.paypaul.Overview;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PayPaulCommand extends AdvancedCommand{

  public PayPaulCommand(Plugin plugin) {
    super(plugin);
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    if(!(sender instanceof Player player)){
      sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be a player to execute this command!"));
      return;
    }

    if(args.isEmpty() || "help".equalsIgnoreCase(args.asString(0))){
      new Help(plugin()).commandRoute(sender, label, args);
      return;
    }

    if("overview".equalsIgnoreCase(args.asString(0))){
      new Overview(plugin()).commandRoute(sender, label, args);
      return;
    }

    new Help(plugin()).commandRoute(sender, label, args);
  }

  @Override
  public @Nullable List<String> tabCompleteRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    return Arrays.asList("overview", "help");
  }
}
