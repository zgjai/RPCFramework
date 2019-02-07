package com.zgjai.rpc.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Rpc invoke metadata
 * consumer调用服务时需要传递的信息：
 * 1. 服务接口类
 * 2. 方法名称
 * 3. 调用参数
 * 4. 服务版本
 */
@AllArgsConstructor
@Getter
@Setter
public class InvokeData implements Serializable {

    private static final long serialVersionUID = 5548813108845836743L;

    private String interfaceName;

    private String methodName;

    private Object[] args;

    private String serviceVersion;
}
