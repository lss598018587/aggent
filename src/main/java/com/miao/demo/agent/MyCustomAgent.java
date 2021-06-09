package com.miao.demo.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class MyCustomAgent {
    /**
     * jvm 参数形式启动，运行此方法
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("premain");
        customLogic(inst);
    }
    /**
     * 打印所有已加载的类名称
     * 修改字节码
     * @param inst
     */
    private static void customLogic(Instrumentation inst) throws UnmodifiableClassException {
        inst.addTransformer(new MyTransformer(), true);
        Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            System.out.println(cls.getName());
        }
    }
}
