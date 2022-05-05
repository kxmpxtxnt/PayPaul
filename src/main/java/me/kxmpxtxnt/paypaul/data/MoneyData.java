package me.kxmpxtxnt.paypaul.data;

import de.chojo.sqlutil.base.DataHolder;
import me.kxmpxtxnt.paypaul.async.BukkitAsyncAction;
import me.kxmpxtxnt.paypaul.transaction.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class MoneyData extends DataHolder {

  private final Plugin plugin;
  private final TransactionLogData transactionLog;

  public MoneyData(DataSource dataSource, Plugin plugin) {
    super(dataSource);
    this.plugin = plugin;
    this.transactionLog = new TransactionLogData(dataSource, plugin);
  }

  public BukkitAsyncAction<Boolean> addMoney(UUID player, long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "insert into paypaul.money set uuid=?, money=? on duplicate key update money = money+?"
      )){
        statement.setString(1, player.toString());
        statement.setLong(2, amount);
        statement.setLong(3, amount);
        return statement.executeUpdate() == 1;
      }catch (SQLException e){
        logDbError(e);
        return false;
      }
    });
  }

  public BukkitAsyncAction<Boolean> setMoney(Player player, long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try (var connection = getConnection(); var statement = connection.prepareStatement(
          "REPLACE paypaul.money(uuid, money) VALUES(?,?)"
      )) {
        statement.setString(1, player.getUniqueId().toString());
        statement.setLong(2, amount);
        return statement.executeUpdate() == 1;
      } catch (SQLException e) {
        logDbError(e);
        return false;
      }
    });
  }

  public BukkitAsyncAction<Long> getMoney(UUID player){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "select money from paypaul.money where uuid=? limit 1"
      )){
        statement.setString(1, player.toString());
        var result = statement.executeQuery();
        if(result.next()){
          return result.getLong("money");
        }

        return 0L;
      }catch (SQLException e){
        logDbError(e);
        return 0L;
      }
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

  public BukkitAsyncAction<Boolean> removeMoney(UUID player, Long amount){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      var enough = new AtomicBoolean(false);

      getMoney(player).queue(money -> {
        enough.set(money >= amount);
      });

      if(!enough.get()){
        return false;
      }

      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "update paypaul.money set money=money-? where uuid=?"
      )) {
        statement.setLong(1, amount);
        statement.setString(2, player.toString());
        return statement.executeUpdate() == 1;
      }catch (SQLException e){
        logDbError(e);
        return false;
      }
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
