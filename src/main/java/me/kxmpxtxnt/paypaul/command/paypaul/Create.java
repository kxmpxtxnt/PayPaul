package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.command.paypaul.create.CreateRequest;
import me.kxmpxtxnt.paypaul.command.paypaul.create.CreateSend;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Create extends AdvancedCommand {
  public Create(Plugin plugin) {
    super(plugin, CommandMeta.builder("create")
        .buildSubCommands((commands, self) -> {
          commands.add(new CreateSend(plugin));
          commands.add(new CreateRequest(plugin));
        })
        .build());
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    /*TODO:
      - add System which will save receiver/amount to send while player sends other value
     */

    Player receiver = /*TODO*/ null;
    long amount = /*TODO*/ 0;

    //((Player)sender).performCommand("/paypaul send " + receiver.getName() + " " + amount);
  }

  @Override
  public @Nullable List<String> tabCompleteRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    return Arrays.asList("send", "request");
  }
}
