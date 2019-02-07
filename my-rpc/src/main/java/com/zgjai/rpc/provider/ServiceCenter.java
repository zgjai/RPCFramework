package com.zgjai.rpc.provider;

import com.google.common.collect.Maps;
import com.zgjai.rpc.bean.InvokeData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by zhangguijiang on 2019/2/7.
 */
public class ServiceCenter implements Runnable {

    private Map<String, Object> serviceMap;
    private int port;

    public ServiceCenter(Map<String, Object> serviceMap, int port) {
        this.serviceMap = serviceMap;
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try(Socket socket = serverSocket.accept()) {
                    try(ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                        InvokeData invokeData = (InvokeData) inputStream.readObject();
                        Object result = invoke(invokeData);
                        try(ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                            outputStream.writeObject(result);
                            outputStream.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object invoke(InvokeData data) throws Exception {
        String serviceKey = data.getInterfaceName() + ":" + data.getServiceVersion();
        Object targetBean = serviceMap.get(serviceKey);
        if (targetBean == null) {
            return null;
        }
        Object[] args = data.getArgs();
        Class<?>[] types = new Class[args.length];
        for (int i=0; i<args.length; i++) {
            types[i] = args[i].getClass();
        }
        Method method = targetBean.getClass().getMethod(data.getMethodName(), types);
        if (method == null) {
            return null;
        }
        return method.invoke(targetBean, args);
    }
}
