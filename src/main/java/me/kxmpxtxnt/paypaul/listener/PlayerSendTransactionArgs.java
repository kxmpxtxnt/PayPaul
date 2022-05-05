package me.kxmpxtxnt.paypaul.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSendTransactionArgs implements Listener {

  @EventHandler
  public void onChat(AsyncChatEvent event){
    var player = event.getPlayer();
    var message = (TextComponent) event.message();

    var args = message.content().split(" ");

    if(args.length != 2){
      event.setCancelled(true);
      player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please provide 2 arguments. Example: Notch 12 <newline>" +
          "Notch is the player you want send money. 12 is the amount of money. The amount needs to be bigger than 0."));
      return;
    }

    var amount = 0L;

    try {
      amount = Long.parseLong(args[1]);
    }catch (NumberFormatException e){
      player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please provide 2 arguments. Example: Notch 12 <newline>" +
          "Notch is the player you want send money. 12 is the amount of money. The amount needs to be bigger than 0."));
      return;
    }

    if(!(amount > 0)){
      player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please provide 2 arguments. Example: Notch 12 <newline>" +
          "Notch is the player you want send money. 12 is the amount of money. The amount needs to be bigger than 0."));
      return;
    }

    if(!player.hasMetadata("create:type")){
      return;
    }

    var metadata = player.getMetadata("create:type").get(0);

    switch (metadata.asString()){
      case "send" -> {
        player.performCommand("/paypaul send " + args[0] + " " + args[1]);
      }

      case "request" -> {
        player.performCommand("/paypaul request " + args[0] + " " + args[1]);
      }

      default -> player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please provide 2 arguments. Example: Notch 12 <newline>" +
              "Notch is the player you want send money. 12 is the amount of money. The amount needs to be bigger than 0."));
    }
  }
}
