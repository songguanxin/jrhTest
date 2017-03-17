package daywork;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import daywork.day17.Person;

/**
 * desc:
 *
 * 给你一个Person类。请用反射的形式:
 1.打印出Person类中所有内容（包括：类名、属性、构造方法）
 2.打印出Person中所有方法（修饰符+返回值+方法名称+参数类型+抛出的异常）
 3.构造出Person对象，调用set方法为name 和 age 赋值
 4.调用sayHello() 方法
 * Created by jiarh on 17/3/1:39.
 */

public class Day17 {


    @Test
    public void test1(){

        try {
            Class c = Class.forName("daywork.day17.Person");
            Object o = c.newInstance();

            String  name = o.getClass().getSimpleName();
            System.out.println(name);
            System.out.println("*****************************");

            Field[] fields = c.getDeclaredFields();
            for (Field f:fields){
                System.out.print(Modifier.toString(f.getModifiers())+" ");
                System.out.print(f.getType().getSimpleName()+"  ");
                System.out.println(f.getName());
            }

            System.out.println("*****************************");

            Constructor[] declaredConstructors = c.getDeclaredConstructors();

            for (Constructor construc : declaredConstructors){
                System.out.println("构造方法的名字"+construc.getName());
            }

            System.out.println("*****************************");

            Method[] methods = c.getMethods();
            for (Method m :methods){
                m.setAccessible(true);
                System.out.println(
                        Modifier.toString(m.getModifiers())+" "
                        +m.getReturnType().toString()+" "
                        +m.getName().toString()+" "
                );

                Class<?>[] parameterTypes = m.getParameterTypes();
                for (Class cc :parameterTypes){
                    System.out.println("参数类型");
                    System.out.println(cc.getSimpleName()+"  ");
                }

                Class<?>[] exceptionTypes = m.getExceptionTypes();

                for (Class e: exceptionTypes){
                    System.out.println("返回异常类型");
                    System.out.println(e.getSimpleName());
                }
            }

            System.out.println("********************");


            Constructor<daywork.day17.Person> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            Person p = constructor.newInstance();


            Method method1 = c.getDeclaredMethod("setName",String.class);
            method1.invoke(p,"jia");
            Method method2= c.getDeclaredMethod("setAge",int.class);
            method2.invoke(p,20);

            System.out.println(c.getDeclaredMethod("getName").invoke(p));
            System.out.println(c.getDeclaredMethod("getAge").invoke(p));

            Method method = c.getDeclaredMethod("sayHello");
            method.setAccessible(true);

            method.invoke(p);
            System.out.println();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
