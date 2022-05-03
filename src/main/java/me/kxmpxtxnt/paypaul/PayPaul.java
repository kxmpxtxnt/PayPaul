package me.kxmpxtxnt.paypaul;

import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.messageblocker.MessageBlockerAPI;
import de.eldoria.messageblocker.blocker.MessageBlocker;
import me.kxmpxtxnt.paypaul.command.PayPaulCommand;
import org.jetbrains.annotations.Nullable;

public final class PayPaul extends EldoPlugin {

  private MessageBlocker messageBlocker = null;

  @Override
  public void onPluginEnable() throws Throwable {
    if(getPluginManager().getPlugin("ProtocolLib") == null){
      getLogger().warning("ProtocolLib is required. Please add it to the plugins!");
      getPluginManager().disablePlugin(this);
      return;
    }

    messageBlocker = MessageBlockerAPI.create(this);

    registerCommand("paypaul", new PayPaulCommand(this));
  }

  public String getCurrency(){
    return getConfig().getString("currency");
  }

  @Nullable
  public MessageBlocker getBlocker(){
    return this.messageBlocker;
  }
}
