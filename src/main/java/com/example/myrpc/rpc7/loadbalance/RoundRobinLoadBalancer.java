package com.example.myrpc.rpc7.loadbalance;

import java.util.List;

public class RoundRobinLoadBalancer implements LoadBalance {
    private int index = 0;

    @Override
    public String balance(List<String> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            throw new IllegalArgumentException("Address list is empty");
        }
        // 获取当前索引对应的地址
        String selectedAddress = addressList.get(index);
        // 更新索引，循环选择下一个地址
        index = (index + 1) % addressList.size();
        return selectedAddress;
    }
}
