package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.PayPaul;
import me.kxmpxtxnt.paypaul.util.Constants;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicLong;

public class Balance extends AdvancedCommand {

  private final PayPaul plugin = (PayPaul) plugin();

  public Balance(Plugin plugin) {
    super(plugin, CommandMeta.builder("balance").build());
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    var player = (Player)sender;
    AtomicLong balance = new AtomicLong(0L);
    plugin.getMoneyData().getMoney(player).queue(balance::set);

    var component = MiniMessage.miniMessage().deserialize(
        Constants.header("Balance") +
            "<br>" +
            "<red>Your balance: <white>bold>" + balance.get() + "<reset>" +
            "<br>" +
            "<hover:show_text:'<#8B8A8A>Click to go back to the overview.'>[<click:run_command:/paypaul><gray>[Back]<reset>" +
            "<br>" +
            Constants.header("Overview"));

    player.sendMessage(component);
  }
}
