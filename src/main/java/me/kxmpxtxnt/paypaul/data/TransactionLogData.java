package me.kxmpxtxnt.paypaul.data;

import de.chojo.sqlutil.base.DataHolder;
import me.kxmpxtxnt.paypaul.async.BukkitAsyncAction;
import me.kxmpxtxnt.paypaul.transaction.Transaction;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.SQLException;

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
        statement.setString(2, transaction.sender().getUniqueId().toString());
        statement.setString(3, transaction.receiver().getUniqueId().toString());
        statement.setLong(4, transaction.amount());

        statement.execute();
        return true;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
