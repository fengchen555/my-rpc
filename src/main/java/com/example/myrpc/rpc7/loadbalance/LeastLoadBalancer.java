package com.example.myrpc.rpc7.loadbalance;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LeastLoadBalancer implements TrackedLoadBalance {
    private Map<String, Integer> loadMap = new ConcurrentHashMap<>();

    @Override
    public String balance(List<String> addressList) {
        addressList.forEach(this::initLoadCount); // Ensure all are initialized
        return loadMap.entrySet().stream()
                       .min(Map.Entry.comparingByValue())
                       .map(Map.Entry::getKey)
                       .orElseThrow(() -> new IllegalStateException("No available service addresses found"));
    }

    @Override
    public void incrementLoad(String address) {
        loadMap.computeIfPresent(address, (k, v) -> v + 1);
    }

    @Override
    public void decrementLoad(String address) {
        loadMap.computeIfPresent(address, (k, v) -> v > 0 ? v - 1 : 0);
    }

    @Override
    public void initLoadCount(String address) {
        loadMap.putIfAbsent(address, 0);
    }
}
