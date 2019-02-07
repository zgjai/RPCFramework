package com.github.zgjai.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Created by zhangguijiang on 2018/10/9.
 */
public class RpcClient {

    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("InterfaceClass is null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("The " + interfaceClass.getName() + "must be interface");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host is null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try (Socket socket = new Socket(host, port)) {
                    try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                            Object result = input.readObject();
                            if (result instanceof Throwable) {
                                throw (Throwable) result;
                            }
                            return result;
                        }
                    }
                }
            }
        });
    }
}
