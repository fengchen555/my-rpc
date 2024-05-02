package com.example.myrpc.rpc7.register;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.example.myrpc.rpc7.loadbalance.LoadBalance;
import com.example.myrpc.rpc7.loadbalance.RandomLoadBalancer;
import com.example.myrpc.rpc7.loadbalance.RoundRobinLoadBalancer;
import jakarta.annotation.PreDestroy;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class NacosServiceRegister implements ServiceRegister {
    private NamingService namingService;
    private static final String SERVER_ADDR = "127.0.0.1:8848"; // Nacos服务器地址
    private LoadBalance loadBalance;

    public NacosServiceRegister() {
        try {
            // 初始化Nacos客户端
            Properties properties = new Properties();
            properties.setProperty("serverAddr", SERVER_ADDR);
            namingService = NacosFactory.createNamingService(properties);
            loadBalance = new RoundRobinLoadBalancer(); // Initialize your load balancer here
            System.out.println("Nacos 连接成功");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            // 注册服务
            namingService.registerInstance(serviceName, serverAddress.getHostName(),
                    serverAddress.getPort());
            System.out.println(serviceName + getServiceAddress(serverAddress) + "服务：注册成功");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            // 获取所有健康的服务实例
            List<Instance> instances = namingService.selectInstances(serviceName, true);
            List<String> healthyAddresses = instances.stream()
                    .map(instance -> instance.getIp() + ":" + instance.getPort())
                    .collect(Collectors.toList());
            String chosenAddress = loadBalance.balance(healthyAddresses);
            String[] ipPort = chosenAddress.split(":");
            return new InetSocketAddress(ipPort[0], Integer.parseInt(ipPort[1]));
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param serviceName
     * @param inetSocketAddress
     */
    @Override
    public void deregister(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.deregisterInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
            System.out.println(serviceName + getServiceAddress(inetSocketAddress) + "服务：注销成功");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    // 关闭Nacos连接
    @Override
    public void close() {
        if (namingService != null) {
            try {
                namingService.shutDown();
                System.out.println("Nacos 连接关闭");
            } catch (NacosException e) {
                e.printStackTrace();
            }
        }
    }

    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }
}
