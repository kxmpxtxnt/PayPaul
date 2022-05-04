package me.kxmpxtxnt.paypaul;

import com.zaxxer.hikari.HikariDataSource;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.messageblocker.MessageBlockerAPI;
import de.eldoria.messageblocker.blocker.MessageBlocker;
import me.kxmpxtxnt.paypaul.command.PayPaulCommand;
import me.kxmpxtxnt.paypaul.data.util.DatabaseSetup;
import me.kxmpxtxnt.paypaul.data.util.DatasourceProvider;
import org.jetbrains.annotations.Nullable;

public final class PayPaul extends EldoPlugin {

  private MessageBlocker messageBlocker = null;
  private HikariDataSource dataSource;

  @Override
  public void onPluginEnable() throws Throwable {
    dataSource = DatasourceProvider.provide(this);

    if(dataSource.getConnection() == null){
      getLogger().warning("Database configuration is invalid... Please fix config.yml.");
      getPluginManager().disablePlugin(this);
      return;
    }

    getLogger().info("Database connection successfully...");

    DatabaseSetup.setupDatabase(this, dataSource);

    if(getPluginManager().getPlugin("ProtocolLib") == null){
      getLogger().warning("ProtocolLib is required. Please add it to the plugins!");
      getPluginManager().disablePlugin(this);
      return;
    }

    messageBlocker = MessageBlockerAPI.create(this);

    registerCommand(new PayPaulCommand(this));
  }

  @Override
  public void onPluginDisable() throws Throwable {
    if(!dataSource.isClosed() && dataSource != null){
      dataSource.close();
    }
  }

  public String getCurrency(){
    return getConfig().getString("currency");
  }

  @Nullable
  public MessageBlocker getBlocker(){
    return this.messageBlocker;
  }
}
