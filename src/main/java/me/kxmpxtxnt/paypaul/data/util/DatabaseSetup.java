package me.kxmpxtxnt.paypaul.data.util;

import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseSetup {

  public static boolean setupDatabase(Plugin plugin, DataSource dataSource){
    String sql;
    try (var in = DatabaseSetup.class.getClassLoader().getResourceAsStream("dbsetup.sql")) {
      sql = new String(in != null ? in.readAllBytes() : new byte[0]);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try(var connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);

      for(String statement : sql.split(";")){
        if(statement.isBlank() || statement.isEmpty())continue;
        try(var prepareStatement = connection.prepareStatement(statement)) {
          prepareStatement.execute();
        }
      }
      connection.commit();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
