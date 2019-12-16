package jc;

import org.junit.Test;
import robot.*;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class JC {
    @Test
    public void testJC() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println(System.getProperty("user.dir"));
        JavaCompiler javac= ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager javaFile=javac.getStandardFileManager(null, null, null);
        String filename="Hello.java";
        //编译单元，可以有多个
        Iterable units=javaFile.getJavaFileObjects(filename);
        JavaCompiler.CompilationTask t=javac.getTask(null, javaFile, null, null, null, units);
        t.call();
        javaFile.close();
        URLClassLoader classload=new URLClassLoader(new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/")});
        Class clazz=classload.loadClass("Hello");

        Method method=clazz.getMethod("main", String[].class);
        method.invoke(clazz.newInstance(),(Object)new String[]{});

    }


    @Test
    public void testAI() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        JavaCompiler javac= ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager javaFile=javac.getStandardFileManager(null, null, null);
        String filename="AIDemo.java";
        //编译单元，可以有多个
        Iterable units=javaFile.getJavaFileObjects(filename);
        JavaCompiler.CompilationTask t=javac.getTask(null, javaFile, null, null, null, units);
        t.call();
        javaFile.close();
        URLClassLoader classload=new URLClassLoader(new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/robot/robot.jar"),new URL("file:/"+System.getProperty("user.dir")+"/")});

        System.out.println(ClassloaderUtil.getCurrentClassloaderDetail());



        Class clazz=classload.loadClass("AIDemo");





        Object object=clazz.getDeclaredConstructor().newInstance();

        AIUtil.methods.forEach((key,val)->{

                try {
                    if (val != null) {
                        Method method=clazz.getMethod(key,val);
                    }else{
                        Method method=clazz.getMethod(key);
                        method.invoke(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        });

        //Method method=clazz.getMethod("main", String[].class);
        //method.invoke(clazz.newInstance(),(Object)new String[]{});
    }
}
class AIUtil{
    static Map<String, Class[]> methods=new HashMap<>();
    static String[] methodNames=new String[]{"init","isReady","deal","isGetLord","completeLord","lordInfo","lordResult",
            "playCards","cardsInfo","gameResult"};
    static {
        Class[] init={AISeatInfo.class};
        Class[] deal={AICard[].class};
        Class[] completeLord={int.class};
        Class[] lordInfo={AILordInfo.class};
        Class[] lordResult={int.class};
        Class[] cardsInfo={AICardsInfo.class};
        Class[] gameResult={int.class};
        methods.put("init",init);
        methods.put("isReady",null);
        methods.put("deal",deal);
        methods.put("isGetLord",null);
        methods.put("completeLord",completeLord);
        methods.put("lordInfo",lordInfo);
        methods.put("lordResult",lordResult);
        methods.put("playCards",null);
        methods.put("cardsInfo",cardsInfo);
        methods.put("gameResult",gameResult);
    }


}
class ClassloaderUtil {
    public static String getCurrentClassloaderDetail() {

        StringBuilder classLoaderDetail = new StringBuilder();

        Stack<ClassLoader> classLoaderStack = new Stack<ClassLoader>();

        ClassLoader currentClassLoader = Thread.currentThread()
                .getContextClassLoader();

        classLoaderDetail
                .append("\n-----------------------------------------------------------------\n");

        // Build a Stack of the current ClassLoader chain

        while (currentClassLoader != null) {

            classLoaderStack.push(currentClassLoader);

            currentClassLoader = currentClassLoader.getParent();

        }

        // Print ClassLoader parent chain

        while (classLoaderStack.size() > 0) {

            ClassLoader classLoader = classLoaderStack.pop();

            // Print current

            classLoaderDetail.append(classLoader);

            if (classLoaderStack.size() > 0) {

                classLoaderDetail.append("\n--- delegation ---\n");

            } else {

                classLoaderDetail.append(" **Current ClassLoader**");

            }

        }

        classLoaderDetail
                .append("\n-----------------------------------------------------------------\n");

        return classLoaderDetail.toString();

    }
}
