package me.kxmpxtxnt.paypaul.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import me.kxmpxtxnt.paypaul.command.paypaul.Balance;
import me.kxmpxtxnt.paypaul.command.paypaul.Create;
import me.kxmpxtxnt.paypaul.command.paypaul.Overview;
import me.kxmpxtxnt.paypaul.command.paypaul.Send;
import org.bukkit.plugin.Plugin;

public class PayPaulCommand extends AdvancedCommand{


  public PayPaulCommand(Plugin plugin) {
    super(plugin, CommandMeta.builder("paypaul")
        .allowPlayer()
        .allowConsole()
        .buildSubCommands((commands, self) -> {
          var overview = new Overview(plugin);

          commands.add(overview);
          commands.add(new Send(plugin));
          commands.add(new Balance(plugin));
          //commands.add(new Create(plugin));

          self.withDefaultCommand(overview);
        })
        .build());
  }
}
