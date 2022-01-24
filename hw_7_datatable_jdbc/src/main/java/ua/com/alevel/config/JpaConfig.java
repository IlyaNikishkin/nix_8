package ua.com.alevel.config;

import java.sql.Connection;
import java.sql.Statement;

public interface JpaConfig {

    void connect();

    Connection getConnection();

    Statement getStatement();
}
