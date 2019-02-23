package com.zgjai.rpc.consumer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import com.zgjai.rpc.bean.InvokeData;

/**
 * Created by zhangguijiang on 2019/2/7.
 */
public class ConsumerProxy {

    public static <T> T getProxy(Class<T> interfaceCls, String host, int port, String serviceVersion, int timeout) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[] { interfaceCls },
                new ConsumerInvocationHandler(host, port, new InvokeData(interfaceCls.getName(), null, null,
                        serviceVersion)));
    }

    public static class ConsumerInvocationHandler implements InvocationHandler {

        private String host;
        private int port;
        private InvokeData invokeData;

        ConsumerInvocationHandler(String host, int port, InvokeData invokeData) {
            this.host = host;
            this.port = port;
            this.invokeData = invokeData;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 动态代理，当consumer实际执行时，调用该invoke方法
            invokeData.setMethodName(method.getName());
            invokeData.setArgs(args);
            // 发送网络请求，传递invokeData
            Socket socket = null;
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                socket = new Socket(host, port);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(invokeData);

                objectInputStream = new ObjectInputStream(socket.getInputStream());
                return objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
