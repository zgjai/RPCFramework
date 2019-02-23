package com.zgjai.rpc.service;

import com.zgjai.rpc.annotation.RPCProvider;
import com.zgjai.rpc.api.GoodByeService;

/**
 * Created by zhangguijiang on 2019/2/23.
 */

@RPCProvider(serviceInterface = GoodByeService.class)
public class GoodByeServiceImpl implements GoodByeService {

    @Override
    public String sayGoodBye(String name) {
        return "GoodBye, " + name;
    }
}
