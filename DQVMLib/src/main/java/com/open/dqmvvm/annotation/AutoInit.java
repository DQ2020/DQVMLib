package com.open.dqmvvm.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

class AutoInit {

    AutoInit() {
        System.out.println("AutoInit");
        Class<? extends AutoInit> c = getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            Init annotation = f.getAnnotation(Init.class);
            if (null != annotation) {
                Class clazz = f.getType();
                try {
                    Constructor constructor = clazz.getConstructor(String.class, int.class);
                    Object hello = constructor.newInstance(annotation.str(), annotation.num());
                    f.setAccessible(true);
                    f.set(this, hello);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
