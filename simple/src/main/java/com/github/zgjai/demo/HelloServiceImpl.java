package com.github.zgjai.demo;

/**
 * @author zhangguijiang
 * @date 2018/10/9 下午2:58
 * @description
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
