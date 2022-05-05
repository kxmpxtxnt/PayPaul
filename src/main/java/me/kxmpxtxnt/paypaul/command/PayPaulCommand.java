package me.kxmpxtxnt.paypaul.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import me.kxmpxtxnt.paypaul.PayPaul;
import me.kxmpxtxnt.paypaul.command.paypaul.*;

public class PayPaulCommand extends AdvancedCommand{


  public PayPaulCommand(PayPaul plugin) {
    super(plugin, CommandMeta.builder("paypaul")
        .allowPlayer()
        .allowConsole()
        .buildSubCommands((commands, self) -> {
          var overview = new Overview(plugin);

          commands.add(overview);
          commands.add(new Send(plugin));
          commands.add(new Balance(plugin));
          commands.add(new Log(plugin));
          //commands.add(new Create(plugin));

          self.withDefaultCommand(overview);
        })
        .build());
  }
}
