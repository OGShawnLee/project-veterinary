package com.veterinary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/veterinary",
      "vet_user",
      "v3Ter1n4#$*"
    );
  }
}
