package com.example.myrpc.rpc6.register;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.net.InetSocketAddress;
import java.util.Properties;

public class NacosServiceRegister implements ServiceRegister {
    private NamingService namingService;
    private static final String SERVER_ADDR = "127.0.0.1:8848"; // Nacos服务器地址

    public NacosServiceRegister() {
        try {
            // 初始化Nacos客户端
            Properties properties = new Properties();
            properties.setProperty("serverAddr", SERVER_ADDR);
            namingService = NacosFactory.createNamingService(properties);
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
            System.out.println( serviceName + getServiceAddress(serverAddress)+"服务：注册成功" );
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            // 获取一个健康的服务实例
            Instance instance = namingService.selectOneHealthyInstance(serviceName);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }

    private InetSocketAddress parseAddress(String address) {
        String[] result = address.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }
}