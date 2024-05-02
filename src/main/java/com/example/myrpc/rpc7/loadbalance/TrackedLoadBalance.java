package com.example.myrpc.rpc7.loadbalance;

public interface TrackedLoadBalance extends LoadBalance {
    void incrementLoad(String address);
    void decrementLoad(String address);
    void initLoadCount(String address);
}
