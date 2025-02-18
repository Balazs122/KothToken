package com.PyrexNetwork.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageManager {
    private final Map<UUID, Integer> tokenStorage = new HashMap<>();

    public void addTokens(UUID playerUUID, int amount) {
        tokenStorage.put(playerUUID, tokenStorage.getOrDefault(playerUUID, 0) + amount);
    }

    public void removeTokens(UUID playerUUID, int amount) {
        tokenStorage.put(playerUUID, tokenStorage.getOrDefault(playerUUID, 0) - amount);
    }

    public void setTokens(UUID playerUUID, int amount) {
        tokenStorage.put(playerUUID, amount);
    }

    public int getTokens(UUID playerUUID) {
        return tokenStorage.getOrDefault(playerUUID, 0);
    }
}