package com.miao.demo.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassTransform implements ClassFileTransformer {
    private Instrumentation inst;

    protected ClassTransform(Instrumentation inst) {
        this.inst = inst;
    }

    /**
     * 此方法在redefineClasses时或者初次加载时会调用，也就是说在class被再次加载时会被调用，
     * 并且我们通过此方法可以动态修改class字节码，实现类似代理之类的功能，具体方法可使用ASM或者javasist，
     * 如果对字节码很熟悉的话可以直接修改字节码。
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        HotAgent.clsnames.add(className);
        return null;
    }
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer){
//        System.out.println("正在加载类："+ className);
//        if (!"com/miao/demo/entity/Bean1".equals(className)){
//            return classfileBuffer;
//        }
//
//        CtClass cl = null;
//        try {
//            ClassPool classPool = ClassPool.getDefault();
//            cl =  classPool.getOrNull("Bean1");
//            System.out.println("cl=====>"+cl);
//            if(cl!=null){
//                cl.detach();
//            }
//            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
//            CtMethod ctMethod = cl.getDeclaredMethod("test1");
//            System.out.println("获取方法名称："+ ctMethod.getName());
//
//            ctMethod.insertBefore("System.out.println(\" 动态插入的打印语句 \");");
//            ctMethod.insertAfter("System.out.println($_);");
//            byte[] transformed = cl.toBytecode();
//            return transformed;
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//        return classfileBuffer;
//    }
}
