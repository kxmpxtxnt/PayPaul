package me.kxmpxtxnt.paypaul.command.paypaul.create;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import org.bukkit.plugin.Plugin;

public class CreateRequest extends AdvancedCommand {

  public CreateRequest(Plugin plugin) {
    super(plugin, CommandMeta.builder("request").build());
  }
}
