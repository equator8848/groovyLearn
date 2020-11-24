package com.equator.learn.dynamic.bytecode;

import com.sun.tools.attach.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author libinkai
 * @date 2020/11/4 10:49 上午
 */
@Slf4j
public class ByteCodeAttach {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {
        // 传入目标JVM PID，跨进程字节码增强
        // echo `jps | grep TransformationA | awk '{print $1}'`
        // jar -cvmf MANIFEST.MF agent.jar ByteCodeAgent.class 代理打包
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith("TransformationA")) {
                VirtualMachine vm = VirtualMachine.attach(vmd.id());
                vm.loadAgent("/Users/libinkai/code/groovyLearn/target/classes/com/equator/learn/dynamic/bytecode/agent.jar");
                log.info("代理加载成功");
                //vm.detach();
            }
        }
    }
}
