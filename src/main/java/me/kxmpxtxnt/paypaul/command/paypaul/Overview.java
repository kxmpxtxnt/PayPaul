package me.kxmpxtxnt.paypaul.command.paypaul;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import me.kxmpxtxnt.paypaul.util.Constants;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Overview extends AdvancedCommand {

  public Overview(Plugin plugin) {
    super(plugin, CommandMeta.builder("overview").build());
  }

  @Override
  public void commandRoute(CommandSender sender, String label, Arguments args) throws CommandException {
    var component = MiniMessage.miniMessage().deserialize(
        Constants.header("Overview") + "<br>" +
            "<br>" +
            "<hover:show_text:'<#8B8A8A>Click to view your account balance.'><click:run_command:/paypaul balance><gold>[Balance] <reset><#8B8A8A>» Your Account balance" +
            "<br>" +
            "<hover:show_text:'<#8B8A8A>Click to Send money to another player.'><click:run_command:/paypaul create send><green>[Send] <reset><#8B8A8A>» Send money" +
            "<br>" +
            "<hover:show_text:'<#8B8A8A>Click to request money from a player.'>[<click:run_command:/paypaul create request><yellow>[Request] <reset><#8B8A8A>» Request money" +
            "<br>" +
            Constants.header("Overview"));

    sender.sendMessage(component);
  }
}
