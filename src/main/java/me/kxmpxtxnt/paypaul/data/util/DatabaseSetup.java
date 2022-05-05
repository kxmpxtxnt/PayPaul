package me.kxmpxtxnt.paypaul.data.util;

import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseSetup {

  //joinked from Lilly :3
  public static void setupDatabase(Plugin plugin, DataSource dataSource) throws SQLException, IOException {
    String setup;
    int amount = 0;
    try (var in = DatabaseSetup.class.getClassLoader().getResourceAsStream("setup.sql")) {
      setup = new String(in != null ? in.readAllBytes() : new byte[0]);
    } catch (IOException e) {
      plugin.getLogger().log(Level.SEVERE, "Could not read db setup file.", e);
      throw e;
    }
    try (var conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      for (String query : setup.split(";")) {
        if (query.isBlank()) continue;
        try (var stmt = conn.prepareStatement(query)) {
          amount++;
          stmt.execute();
        }
      }
      conn.commit();
    }
    plugin.getLogger().info("§2Database setup complete. Executed " + amount + " queries.");
  }
}
