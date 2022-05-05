package me.kxmpxtxnt.paypaul.data.util;

import com.zaxxer.hikari.HikariDataSource;
import de.chojo.sqlutil.datasource.DataSourceCreator;
import de.chojo.sqlutil.updater.SqlType;
import org.bukkit.plugin.Plugin;
import org.mariadb.jdbc.MariaDbDataSource;

public class DatasourceProvider {

  public static HikariDataSource provide(Plugin plugin) {
    try {
      Class.forName("org.mariadb.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    return DataSourceCreator.create(SqlType.MARIADB, MariaDbDataSource.class)
        .withAddress(plugin.getConfig().getString("database.host"))
        .withPort(plugin.getConfig().getString("database.port"))
        .withUser(plugin.getConfig().getString("database.user"))
        .withPassword(plugin.getConfig().getString("database.password"))
        .forDatabase(plugin.getConfig().getString("database.database"))
        .create().withMaximumPoolSize(20).withMinimumIdle(2).build();
  }

}
