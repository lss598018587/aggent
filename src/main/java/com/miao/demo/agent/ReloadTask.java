package com.miao.demo.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.TimerTask;

public class ReloadTask extends TimerTask {
    private Instrumentation inst;

    protected ReloadTask(Instrumentation inst) {
        this.inst = inst;
    }

    @Override
    public void run() {
        try {
            ClassDefinition[] cd = new ClassDefinition[1];
            Class[] classes = inst.getAllLoadedClasses();
            for (Class cls : classes) {
                if(cls.getName().equals("com.miao.demo.entity.Bean1")){
                    System.out.println("getClassLoader::"+cls.getClassLoader());
                }
                if (cls.getClassLoader() == null || !cls.getClassLoader().getClass().getName().equals("sun.misc.Launcher$AppClassLoader")
                || !cls.getName().equals("com.miao.demo.entity.Bean1"))
                    continue;
                String name = cls.getName().replaceAll("\\.", "/");
                cd[0] = new ClassDefinition(cls, reload(loadClassBytes(cls, name + ".class")));
                inst.redefineClasses(cd);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] loadClassBytes(Class cls, String clsname) throws Exception {
        System.out.println("-----------------》"+clsname + ":" + cls);
        InputStream is = cls.getClassLoader().getSystemClassLoader().getResourceAsStream(clsname);
        if (is == null) return null;
        byte[] bt = new byte[is.available()];
        is.read(bt);
        is.close();
        return bt;
    }
    private byte[] reload(byte [] classfileBuffer){
        CtClass cl=null;
        try {
            ClassPool classPool = ClassPool.getDefault();
            cl = classPool.get("com.miao.demo.entity.Bean1");
            if(cl!=null){
                cl.detach();
            }
            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtMethod ctMethod = cl.getDeclaredMethod("test1");

            ctMethod.insertBefore("System.out.println(\" 动态插入的打印语句 \");");
            ctMethod.insertAfter("System.out.println(\" 结束！！！！ \");");

            byte[] transformed = cl.toBytecode();
            return transformed;
        }catch (Exception e){
            e.printStackTrace();

        }
        return classfileBuffer;
    }
}