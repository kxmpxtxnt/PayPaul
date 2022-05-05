package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.PayPaul;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Log extends AdvancedCommand {

  private final PayPaul plugin = (PayPaul) plugin();

  public Log(Plugin plugin) {
    super(plugin, CommandMeta.builder("log")
        .withPermission("paypaul.commands.log")
        .allowConsole()
        .allowPlayer()
        .hidden()
        .build());
  }

  @Override
  public void commandRoute(CommandSender commandSender, String label, Arguments args) throws CommandException {
    var count = 3;

    if(args.get(0) != null){
      count = args.asInt(0);
    }

    plugin.getLogData().getTransactions(count).queue(log -> {
      if(log.isEmpty()){
        commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Currently there no transactions."));
        return;
      }

      log.forEach(transaction -> {
        var sender = transaction.sender();
        var receiver= transaction.receiver();

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<blue>[" + transaction.time() + "]: <gold>" +
            sender.getName() + " (" + sender.getUniqueId() + ") <white><bold>-> <reset><gold>" + receiver.getName() + " (" + receiver.getUniqueId() + ") <white><bold>| <yellow><bold>" +
            transaction.amount() + plugin.getCurrency()));
      });
    });
  }
}
