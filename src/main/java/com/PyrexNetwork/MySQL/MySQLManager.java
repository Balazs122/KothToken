package com.PyrexNetwork.MySQL;

import java.sql.*;
import java.util.List;

public class MySQLManager implements com.PyrexNetwork.Storage.StorageManager {
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

    @Override
    public int getTokenBalance(String playerUUID) {
        try (Connection connection = getConnection()) {
            String query = "SELECT tokens FROM koth_tokens WHERE player_uuid = ?";
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

    @Override
    public void addTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE koth_tokens SET tokens = tokens + ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE koth_tokens SET tokens = tokens - ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTokens(String playerUUID, int amount) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE koth_tokens SET tokens = ? WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, amount);
                stmt.setString(2, playerUUID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllPlayers() {
        return List.of();
    }
}
