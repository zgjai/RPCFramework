package com.zgjai.rpc.provider;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangguijiang on 2019/2/7.
 */
public class RegisterCenter {
    private static final RegisterCenter registerCenter = new RegisterCenter();

    private Map<String, Object> serviceMap = Maps.newConcurrentMap();

    private Set<Integer> portSet = Sets.newHashSet();

    private ExecutorService servicePool = Executors.newCachedThreadPool();

    public static RegisterCenter getInstance() {
        return registerCenter;
    }

    public void registerAndPublishService(String serviceInterface, String serviceVersion,
                                          String host, Integer port, Object bean) {
        String serviceKey = serviceInterface + ":" + serviceVersion;
        // 注册服务
        serviceMap.put(serviceKey, bean);
        // 端口未被监听，开启监听
        if (!portSet.contains(port)) {
            servicePool.execute(new ServiceCenter(serviceMap, port));
            portSet.add(port);
        }
    }
}
