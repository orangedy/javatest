/*
 * @(#) GroovyFirstTest.java 2015年2月10日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package groovy;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * groovy测试类
 *
 * @author hzdingyong
 * @version 2015年2月10日
 */
public class GroovyFirstTest {

    public static void main(String[] args) throws IOException, CompilationFailedException, InstantiationException,
                    IllegalAccessException {
        GroovyFirstTest test = new GroovyFirstTest();
        test.executeGroovyShell();
        test.executeGroovyShellFromFile("src/groovy/groovyshell.groovy");
        test.executeGroovyClass();
    }

    /**
     * 通过脚本执行引擎直接执行脚本
     */
    public void executeGroovyShell() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine scriptEngine = factory.getEngineByName("groovy");// 或者"Groovy" groovy版本要1.6以上的

        try {
            System.out.println("calling groovy from java start");
            scriptEngine.put("name", "VerRan");
            scriptEngine.eval("println \"${name}\"+\"你好\";name=name+'！'");
            System.out.println(scriptEngine.get("name"));
            System.out.println("calling groovy from java end");
            scriptEngine.eval("");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过脚本执行引擎直接执行脚本，脚本从文件中读取
     * @param file
     * @throws IOException
     */
    public void executeGroovyShellFromFile(String file) throws IOException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine scriptEngine = factory.getEngineByName("groovy");// 或者"Groovy" groovy版本要1.6以上的

        try {
            System.out.println("calling groovy from java start");
            scriptEngine.put("name", "VerRan");
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("string", "value");
            map.put("int", 1);
            scriptEngine.put("map", map);
            String code = FileUtils.readFileToString(new File(file));
            scriptEngine.eval(code);
            System.out.println(scriptEngine.get("name"));
            System.out.println("calling groovy from java end");
            scriptEngine.eval("");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在Java中直接执行groovy代码，groovy代码编译后与Java代码调用没有什么区别
     * @throws CompilationFailedException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void executeGroovyClass() throws CompilationFailedException, IOException, InstantiationException,
                    IllegalAccessException {
        ClassLoader cl = getClass().getClassLoader();
        GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
        Class groovyClass = groovyCl.parseClass(new File("src/GFoo.groovy"));
        IFoo p = (IFoo) groovyClass.newInstance();

        p.doSomething();// 调用GPerson.groovy实现接口IPerson的方法
    }
}

interface IFoo {
    void doSomething();
}
