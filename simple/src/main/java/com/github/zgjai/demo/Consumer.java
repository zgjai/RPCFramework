package com.github.zgjai.demo;

import com.github.zgjai.client.RpcClient;

/**
 * @author zhangguijiang
 * @date 2018/10/9 下午3:01
 * @description
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        HelloService helloService = RpcClient.refer(HelloService.class, "127.0.0.1", 8888);
        String result = helloService.hello("hehe");
        System.out.println(result);
        Student student = helloService.hello(1);
        System.out.println(student);
    }
}
