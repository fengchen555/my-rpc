package com.example.myrpc.rpc6.server;

import com.example.myrpc.rpc6.register.NacosServiceRegister;
import com.example.myrpc.rpc6.register.ServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServiceProvider {
    // 服务提供者接口名与服务对象的映射
    private Map<String, Object> interfaceProvider;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public ServiceProvider(String host, int port) {
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new NacosServiceRegister();
        // 注册关闭钩子
        registerShutdownHook();
    }

    public void provideServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for (Class clazz : interfaces) {
            String serviceName = clazz.getName();
            // 保存服务名和服务实现类的映射
            interfaceProvider.put(serviceName, service);
            serviceRegister.register(serviceName, new InetSocketAddress(host, port));
        }
    }

    // 关闭特定服务的方法
    private void deregister(String serviceName) {
        interfaceProvider.remove(serviceName);
        serviceRegister.deregister(serviceName, new InetSocketAddress(host, port));
    }

    // 注册关闭钩子
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 在钩子中调用关闭方法
            closeAllServices();
            // 关闭Nacos连接
            serviceRegister.close();
        }));
    }

    // 关闭所有服务的方法，用于钩子调用
    public void closeAllServices() {
        Iterator<String> iterator = interfaceProvider.keySet().iterator();
        while (iterator.hasNext()) {
            String serviceName = iterator.next();
            iterator.remove(); // 使用迭代器的remove方法删除元素
            deregister(serviceName);
        }
    }


    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }
}
