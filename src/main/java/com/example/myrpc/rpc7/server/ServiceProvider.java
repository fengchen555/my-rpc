package com.example.myrpc.rpc7.server;

import com.example.myrpc.rpc7.register.NacosServiceRegister;
import com.example.myrpc.rpc7.register.Service;
import com.example.myrpc.rpc7.register.ServiceRegister;
import com.example.myrpc.rpc7.register.ServiceScan;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ServiceProvider {
    // 服务提供者接口名与服务对象的映射
    private Map<String, Object> interfaceProvider;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public ServiceProvider(String host, int port, Class<?> startupClass) {
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new NacosServiceRegister();
        // 注册关闭钩子
        registerShutdownHook();
//        System.out.println(123);
//        System.out.println("扫描服务类"+startupClass.getName());
        if (startupClass.isAnnotationPresent(ServiceScan.class)) {
//            System.out.println("scan");
            ServiceScan serviceScan = startupClass.getAnnotation(ServiceScan.class);
            scanServices(serviceScan.value());
        }
    }
    //扫描注册服务
    private void scanServices(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
        //System.out.println(classes);
        for (Class<?> clazz : classes) {
            try {
                Object serviceInstance = clazz.getDeclaredConstructor().newInstance();
                serviceRegister.register(clazz.getInterfaces()[0].getName(), new InetSocketAddress(host, port));
                interfaceProvider.put(clazz.getInterfaces()[0].getName(), serviceInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
