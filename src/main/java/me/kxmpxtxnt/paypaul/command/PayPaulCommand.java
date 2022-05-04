package me.kxmpxtxnt.paypaul.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import me.kxmpxtxnt.paypaul.command.paypaul.Help;
import me.kxmpxtxnt.paypaul.command.paypaul.Overview;
import org.bukkit.plugin.Plugin;

public class PayPaulCommand extends AdvancedCommand{


  public PayPaulCommand(Plugin plugin) {
    super(plugin, CommandMeta.builder("paypaul")
        .allowPlayer()
        .allowConsole()
        .addAlias("pp")
        .buildSubCommands((commands, self) -> {
          var overview = new Overview(plugin);

          commands.add(overview);
          commands.add(new Help(plugin));

          self.withDefaultCommand(overview);
        })
        .build());
  }
}
