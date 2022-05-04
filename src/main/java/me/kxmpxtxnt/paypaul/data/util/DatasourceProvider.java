package me.kxmpxtxnt.paypaul.data.util;

import com.zaxxer.hikari.HikariDataSource;
import de.chojo.sqlutil.datasource.DataSourceCreator;
import org.bukkit.plugin.Plugin;
import org.mariadb.jdbc.MariaDbDataSource;

public class DatasourceProvider {

  public static HikariDataSource provide(Plugin plugin) {
    return DataSourceCreator.create(MariaDbDataSource.class)
        .withAddress(plugin.getConfig().getString("database.host"))
        .withPort(plugin.getConfig().getString("database.port"))
        .withUser(plugin.getConfig().getString("database.user"))
        .withPassword(plugin.getConfig().getString("database.password"))
        .create().withMaximumPoolSize(20).withMinimumIdle(2).build();
  }

}
