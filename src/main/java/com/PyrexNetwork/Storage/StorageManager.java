package com.PyrexNetwork.Storage;

import java.util.List;

public interface StorageManager {
    int getTokenBalance(String playerName);
    void addTokens(String playerName, int amount);
    void removeTokens(String playerName, int amount);
    void setTokens(String playerName, int amount);

    // Add getAllPlayers method to StorageManager interface
    List<String> getAllPlayers();  // This will be implemented in both FileManager and MySQLManager
}
