package com.github.zgjai.server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhangguijiang on 2018/10/9.
 */
public class RpcServer {

    public void export(final Object service, int port) throws Exception {
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
                final Socket socket = server.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
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
