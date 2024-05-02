package com.example.myrpc.rpc7.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalance {

    @Override
    public String balance(List<String> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            throw new IllegalArgumentException("Address list is empty");
        }
        // 生成随机索引
        Random random = new Random();
        int index = random.nextInt(addressList.size());
        // 返回对应索引的地址
        return addressList.get(index);
    }
}
