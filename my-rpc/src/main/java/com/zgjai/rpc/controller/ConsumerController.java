package com.zgjai.rpc.controller;

import com.zgjai.rpc.annotation.RPCConsumer;
import com.zgjai.rpc.api.GoodByeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangguijiang on 2019/2/23.
 */

@RestController
public class ConsumerController {

    @RPCConsumer(timeout = 5000)
    private GoodByeService goodByeService;

    @RequestMapping(value = "/sayGoodBye", method = RequestMethod.GET)
    public String sayGoodBye(@RequestParam String name) {
        // RPC调用
        String result = goodByeService.sayGoodBye(name);
        System.out.println(result);
        return result;
    }
}
