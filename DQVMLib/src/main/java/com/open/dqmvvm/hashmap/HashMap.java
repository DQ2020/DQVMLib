package com.open.dqmvvm.hashmap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class HashMap {

    public static void main(String[] args) {
        Data data = new Data("5566",7788);
        Class<? extends Data> clazz = data.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f:fields){
            System.out.println(f.getName());
            System.out.println("\n");
            System.out.println(f.getType().getName());
            System.out.println("\n");
            try {
                f.setAccessible(true);
                System.out.println(f.get(data).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.printf("\n");
        }


        try {
            Field num = clazz.getDeclaredField("num");
            num.setAccessible(true);
            num.set(data,10000);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field str = clazz.getDeclaredField("str");
            str.setAccessible(true);
            str.set(data,"0000");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        try {
            try {
                System.out.println(clazz.getDeclaredMethod("toString").invoke(data).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
