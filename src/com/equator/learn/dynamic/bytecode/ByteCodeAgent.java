package com.equator.learn.dynamic.bytecode;

import com.equator.learn.dynamic.ordinary.TransformationA;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

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
            // 生成VerifyError错误，解决方法：-Xverify:none，原因是没有更新StackMapTable？
            inst.retransformClasses(TransformationA.class);
            log.info("字节码替换成功");
        } catch (Exception e) {
            log.info("字节码替换失败TAT");
        }
    }
}
