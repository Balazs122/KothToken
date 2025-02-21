package com.PyrexNetwork.MySQL;

import com.PyrexNetwork.Storage.StorageManager;

import java.sql.*;
import java.util.List;

public class MySQLManager extends StorageManager {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public MySQLManager(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        return DriverManager.getConnection(url, username, password);
    }

    public int getTokenBalance(String playerUUID) {
        try (Connection connection = getConnection()) {
            String query = "SELECT tokens FROM pyrex_tokens WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, playerUUID);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("tokens");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE pyrex_tokens SET tokens = tokens + ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE pyrex_tokens SET tokens = tokens - ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE pyrex_tokens SET tokens = ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllPlayers() {
        return List.of();
    }
}
