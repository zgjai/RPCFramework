package com.zgjai.rpc.bean;

import java.io.Serializable;

/**
 * Rpc invoke metadata
 * consumer调用服务时需要传递的信息：
 * 1. 服务接口类
 * 2. 方法名称
 * 3. 调用参数
 * 4. 服务版本
 */
public class InvokeData implements Serializable {

    private static final long serialVersionUID = 5548813108845836743L;

    private String interfaceName;

    private String methodName;

    private Object[] args;

    private String serviceVersion;

    public InvokeData(String interfaceName, String methodName, Object[] args, String serviceVersion) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.args = args;
        this.serviceVersion = serviceVersion;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }
}
