package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.PayPaul;
import me.kxmpxtxnt.paypaul.transaction.Transaction;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Send extends AdvancedCommand {

  private final PayPaul plugin = (PayPaul) plugin();

  public Send(Plugin plugin) {
    super(plugin, CommandMeta.builder("send").build());
  }

  @Override
  public @Nullable List<String> tabCompleteRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException{
    var player = (Player)sender;

    if((args.get(0) == null && args.get(1) == null) || args.isEmpty()){
      player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please use /paypaul send <player> <amount>"));
      return;
    }

    var receiver = args.asPlayer(0);
    var amount = args.asLong(1);

    plugin.getMoneyData().transferMoney(new Transaction(player.getUniqueId(), receiver.getUniqueId(), amount)).queue(success -> {
      if(!success){
        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Something went wrong :("));
        return;
      }

      player.sendMessage(MiniMessage.miniMessage().deserialize("<green>You send <gold>" + amount + plugin.getCurrency() + " <green>to <blue>" + receiver.name()));
      receiver.sendMessage(MiniMessage.miniMessage().deserialize("<green>You received <gold>" + amount + plugin.getCurrency() + " <green>from <blue>" + player.name()));
    });
  }
}
