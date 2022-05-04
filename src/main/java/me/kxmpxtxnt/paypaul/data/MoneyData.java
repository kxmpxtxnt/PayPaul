package me.kxmpxtxnt.paypaul.data;

import de.chojo.sqlutil.base.DataHolder;
import me.kxmpxtxnt.paypaul.async.BukkitAsyncAction;
import me.kxmpxtxnt.paypaul.transaction.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MoneyData extends DataHolder {

  private final Plugin plugin;
  private final TransactionLogData transactionLog;

  public MoneyData(DataSource dataSource, Plugin plugin, TransactionLogData transactionLog) {
    super(dataSource);
    this.plugin = plugin;
    this.transactionLog = transactionLog;
  }

  public BukkitAsyncAction<Boolean> addMoney(Player player, long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "insert into paypaul.economy set uuid=?, money=? on duplicate key update money = money+?"
      )){
        statement.setString(1, player.getUniqueId().toString());
        statement.setLong(2, amount);
        statement.setLong(3, amount);
        return statement.executeUpdate() == 1;
      }catch (SQLException e){
        logDbError(e);
      }

      return false;
    });
  }

  public BukkitAsyncAction<Boolean> setMoney(Player player, long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try (var connection = getConnection(); var statement = connection.prepareStatement(
          "REPLACE paypaul.economy(uuid, money) VALUES(?,?)"
      )) {
        statement.setString(1, player.getUniqueId().toString());
        statement.setLong(2, amount);
        return statement.executeUpdate() == 1;
      } catch (SQLException e) {
        logDbError("Could not set Money", e);
      }

      return false;
    });
  }

  public BukkitAsyncAction<Long> getMoney(Player player){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "select money from paypaul.economy where uuid=? limit 1"
      )){
        statement.setString(1, player.getUniqueId().toString());
        return statement.executeQuery().getLong(1);
      }catch (SQLException e){
       logDbError(e);
      }
      return 0L;
    });
  }

  public BukkitAsyncAction<Boolean> transferMoney(Transaction transaction){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      var success = new AtomicBoolean(false);
      transactionLog.addLog(transaction);

      removeMoney(transaction.sender(), transaction.amount()).queue(success::set);

      if(!success.get()){
        return false;
      }

      addMoney(transaction.receiver(), transaction.amount()).queue(success::set);
      return success.get();
    });
  }

  public BukkitAsyncAction<Boolean> removeMoney(Player player, Long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      var enough = new AtomicBoolean(false);

      getMoney(player).queue(money -> {
        enough.set(money >= amount);
      });

      if(!enough.get()){
        return false;
      }

      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "update paypaul.economy set money=money-? where uuid=?"
      )) {
        statement.setLong(1, amount);
        statement.setString(2, player.getUniqueId().toString());
        return statement.executeUpdate() == 1;
      }catch (SQLException e){
        logDbError(e);
      }
      return false;
    });
  }
  
  public BukkitAsyncAction<Boolean> clearMoney(Player player){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      var success = new AtomicBoolean(false);
      setMoney(player, 0).queue(success::set);
      return success.get();
    });
  }

}
