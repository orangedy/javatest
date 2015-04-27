/*
 * @(#) ReflectTest.java 2015年4月17日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2015年4月17日
 */
public class ReflectTest {

    class TestBaseClass {
        private int fieldA = 1;
        private String fieldB = "baseB";

        public int getFieldA() {
            return fieldA;
        }

        public void setFieldA(int fieldA) {
            this.fieldA = fieldA;
        }

        public String getFieldB() {
            return fieldB;
        }

        public void setFieldB(String fieldB) {
            this.fieldB = fieldB;
        }
    }

    class TestSubClass extends TestBaseClass {
        private int fieldA = 2;
        private String fieldB = "subB";

        public int getFieldA() {
            return fieldA;
        }

        public void setFieldA(int fieldA) {
            this.fieldA = fieldA;
        }

        public String getFieldB() {
            return fieldB;
        }

        public void setFieldB(String fieldB) {
            this.fieldB = fieldB;
        }
    }

    public String getValue(Object object, String fieldName) {
        String content = "";
        Field[] superFields = null, fields = null;
        List<Field> fieldList = null;
        try {
            superFields = object.getClass().getSuperclass().getDeclaredFields();
            fields = object.getClass().getDeclaredFields();

            fieldList = new ArrayList<Field>();
            Collections.addAll(fieldList, superFields);
            Collections.addAll(fieldList, fields);

            for (Field field : fieldList) {
                if (field.getName().equals(fieldName)) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), object.getClass());
                    Method method = pd.getReadMethod();
                    Object ret = method.invoke(object);
                    if (ret != null) {
                        content = ret.toString();
                    } else {
                        content = "";
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            superFields = null;
            fields = null;
            fieldList = null;
        }
        return content;
    }

    public String getFieldValue(Object object, String fieldName) {
        String content = "";
        Field[] superFields = null, fields = null;
        List<Field> fieldList = null;
        try {
            superFields = object.getClass().getSuperclass().getDeclaredFields();
            fields = object.getClass().getDeclaredFields();

            fieldList = new ArrayList<Field>();
            Collections.addAll(fieldList, superFields);
            Collections.addAll(fieldList, fields);

            for (Field field : fieldList) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    content = value.toString();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            superFields = null;
            fields = null;
            fieldList = null;
        }
        return content;
    }

    @Test
    public void testGetFieldValue() {
        TestSubClass sub = new TestSubClass();
        System.out.println(getFieldValue(sub, "fieldA"));
        System.out.println(getValue(sub, "fieldA"));
    }
}
