package me.kxmpxtxnt.paypaul.data;

import de.chojo.sqlutil.base.DataHolder;
import me.kxmpxtxnt.paypaul.async.BukkitAsyncAction;
import me.kxmpxtxnt.paypaul.transaction.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class TransactionLogData extends DataHolder {

  private final Plugin plugin;

  public TransactionLogData(DataSource dataSource, Plugin plugin) {
    super(dataSource);
    this.plugin = plugin;
  }

  public BukkitAsyncAction<Boolean> addLog(Transaction transaction){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "insert into paypaul.log(time, sender, receiver, amount) VALUES(?,?,?,?)"
      )) {
        statement.setLong(1, transaction.time());
        statement.setString(2, transaction.sender().toString());
        statement.setString(3, transaction.receiver().toString());
        statement.setLong(4, transaction.amount());

        statement.execute();
        return true;
      } catch (SQLException e) {
        logDbError(e);
        return false;
      }
    });
  }

  public BukkitAsyncAction<HashSet<Transaction>> getTransactions(int count){
    return BukkitAsyncAction.supplyAsync(plugin, () -> {
      var transactions = new HashSet<Transaction>(Collections.emptySet());
      try(var connection = getConnection(); var statement = connection.prepareStatement(
          "select time, sender, receiver, amount from paypaul.log limit ?"
      )) {
        statement.setInt(1, count);
        var result = statement.executeQuery();

        while (result.next()){
          var time = result.getLong("time");
          var sender = UUID.fromString(result.getString("sender"));
          var receiver = UUID.fromString(result.getString("receiver"));
          var amount = result.getLong("amount");

          transactions.add(new Transaction(sender, receiver, amount, time));
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return transactions;
    });
  }
}
