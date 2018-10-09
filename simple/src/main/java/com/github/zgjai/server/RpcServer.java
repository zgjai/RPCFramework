package com.github.zgjai.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhangguijiang on 2018/10/9.
 */
public class RpcServer {

    public static void export(final Object service, int port) throws Exception {
        if (service == null) {
            throw new IllegalArgumentException("service instance is null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port:" + port);
        }
        System.out.println("Export service" + service.getClass().getName() + "on port " + port);
        ServerSocket server = new ServerSocket(port);
        for (;;) {
            try {
                new Thread(() -> {
                    try {
                        try (Socket socket = server.accept()) {
                            try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                                String methodName = input.readUTF();
                                Class<?>[] paramTypes = (Class<?>[]) input.readObject();
                                Object[] args = (Object[]) input.readObject();
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                try {
                                    Method method = service.getClass().getMethod(methodName, paramTypes);
                                    Object result = method.invoke(service, args);
                                    output.writeObject(result);
                                } catch (Throwable t) {
                                    output.writeObject(t);
                                } finally {
                                    output.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
