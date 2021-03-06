package me.kxmpxtxnt.paypaul;

import com.zaxxer.hikari.HikariDataSource;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.messageblocker.MessageBlockerAPI;
import de.eldoria.messageblocker.blocker.MessageBlocker;
import me.kxmpxtxnt.paypaul.command.PayPaulCommand;
import me.kxmpxtxnt.paypaul.data.MoneyData;
import me.kxmpxtxnt.paypaul.data.TransactionLogData;
import me.kxmpxtxnt.paypaul.data.util.DatabaseSetup;
import me.kxmpxtxnt.paypaul.data.util.DatasourceProvider;
import me.kxmpxtxnt.paypaul.listener.PlayerSendTransactionArgs;

public final class PayPaul extends EldoPlugin {

  private MessageBlocker messageBlocker = null;
  private HikariDataSource dataSource;

  private MoneyData moneyData;
  private TransactionLogData logData;

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

    moneyData = new MoneyData(dataSource, this);
    logData = new TransactionLogData(dataSource, this);

    messageBlocker = MessageBlockerAPI.create(this);

    registerCommand(new PayPaulCommand(this));
    registerListener(new PlayerSendTransactionArgs());
  }

  @Override
  public void onPluginDisable() throws Throwable {
    if(dataSource != null && !dataSource.isClosed()) {
      dataSource.close();
    }
  }

  public String getCurrency(){
    return getConfig().getString("currency");
  }

  public MessageBlocker getBlocker(){
    return this.messageBlocker;
  }

  public MoneyData getMoneyData() {
    return moneyData;
  }

  public TransactionLogData getLogData() {
    return logData;
  }
}
