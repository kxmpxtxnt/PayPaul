package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.PayPaul;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Create extends AdvancedCommand {

  private final PayPaul plugin = (PayPaul) plugin();
  public Create(Plugin plugin) {
    super(plugin, CommandMeta.builder("create").build());
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    ((Player)sender).setMetadata("create:type", new FixedMetadataValue(plugin, args.asString(2)));
  }

  @Override
  public @Nullable List<String> tabCompleteRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    return Arrays.asList("send", "request");
  }
}
