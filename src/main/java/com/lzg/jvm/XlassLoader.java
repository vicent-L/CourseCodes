package com.lzg.jvm;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法
 */
public class XlassLoader extends ClassLoader {
    // 相关参数
    public static final String className = "Hello";
    public static final String methodName = "hello";

    public static void main(String[] args) throws Exception {

        Class clazz = new XlassLoader().findClass(className);
        Object instance = clazz.newInstance();
        // 调用方法
        Method method = clazz.getMethod(methodName);
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String suffix = ".xlass";
        // 获取输入流
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(name + suffix);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            byte[] classBytes = decode(result);
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            close(inputStream);
        }
    }

    // 解码
    private static byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (0xff - byteArray[i]);
        }
        return targetArray;
    }

    // 关闭流
    private static void close(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}