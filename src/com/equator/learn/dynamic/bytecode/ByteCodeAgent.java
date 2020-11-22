package com.equator.learn.dynamic.bytecode;

import com.equator.learn.dynamic.ordinary.TransformationA;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author libinkai
 * @date 2020/11/4 10:15 上午
 */
@Slf4j
public class ByteCodeAgent {
    public static void agentmain(String args, Instrumentation inst) {
        // 指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new ByteCodeTransformer(), true);
        try {
            //重定义类并载入新的字节码
            inst.retransformClasses(TransformationA.class);
            log.info("字节码替换成功");
        } catch (Exception e) {
            log.info("字节码替换失败TAT");
        }
    }
}
