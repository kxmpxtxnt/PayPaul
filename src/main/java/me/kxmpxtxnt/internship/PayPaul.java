package me.kxmpxtxnt.internship;

import de.eldoria.messageblocker.MessageBlockerAPI;
import de.eldoria.messageblocker.blocker.MessageBlocker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class PayPaul extends JavaPlugin {

  private MessageBlocker messageBlocker = null;
  @Override
  public void onEnable() {
    if(Bukkit.getPluginManager().getPlugin("ProtocolLib") == null){
      getLogger().warning("ProtocolLib is required. Please add it to the plugins!");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }

    messageBlocker = MessageBlockerAPI.create(this);
  }

  public String getCurrency(){
    return getConfig().getString("currency");
  }

  @Nullable
  public MessageBlocker getBlocker(){
    return this.messageBlocker;
  }
}
