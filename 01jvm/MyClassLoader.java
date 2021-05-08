package javaadvanced.code.week1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
    //自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
    @Autowired
    ResourceLoader resourceLoader;
    @Override
    protected Class<?> findClass(String name)  throws ClassNotFoundException{


        File file1 =new File("../resources/Hello.xlass");
        byte[] bytes = readFile(file1);
        return defineClass(name,bytes,0,bytes.length);



    }
    public byte[] readFile(File file)  {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hello.xlass");
        try {
            byte[] bytes = new byte[(int)inputStream.available()];
            inputStream.read(bytes);
            for(int i=0;i<bytes.length;i++){
                bytes[i]=(byte) (255-bytes[i]);
            }
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[1];



    }
    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader=new MyClassLoader();
        Class<?> clazz = myClassLoader.loadClass("Hello");
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println(clazz.getSimpleName() + "." + m.getName());
        }
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(instance);

    }



}