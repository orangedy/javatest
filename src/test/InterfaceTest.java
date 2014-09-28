package test;

/**
 * 接口测试类，接口的方法默认为public abstract
 * 属性默认为public static final
 * 但是接口本身的可见性定义只能为public或者默认，两者含义不变
 *
 * @author hzdingyong
 * @version 2014年9月10日
 */
public interface InterfaceTest {
    String NAME = "codemonkeyism";

    public static final String age = "xxx";

    public int age2 = 10;
}