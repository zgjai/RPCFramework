package com.github.zgjai.demo;

import com.github.zgjai.server.RpcServer;

/**
 * @author zhangguijiang
 * @date 2018/10/9 下午2:59
 * @description
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        RpcServer.export(helloService, 8888);
    }

}
