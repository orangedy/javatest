package test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 泛型测试类
 *
 * @author hzdingyong
 * @version 2015年1月22日
 * @param <T>
 */
public class GenericsTest<T extends Object> {

    private T t;
    private List<Object> list;
    private List<GenericsTest<T>> list2;

    private List<List> lists = new LinkedList<List>();
    private List<Base> defaultList;

    public void testCreateT() {
        // can't Instance a T type
//        T t = new T();
    }

    public void getT(T t) {
        T param = t;

        // do something
    }

    public <E> void variParam(E... ts) {
        System.out.println(ts.length);
    }

    public void addToList() {
        String obj1 = "test";
        list.add(obj1);
        GenericsTest<Object> obj2 = new GenericsTest<Object>();
//         list2.add(obj2); 
    }

    public static void main(String[] args) {
        GenericsTest<String> test = new GenericsTest<String>();
        test.variParam("1", "2", "3");
        test.testPutAndGet();
    }

    public <T extends Base> void put(List<T> list) {
        lists.add(list);
        defaultList = new LinkedList<Base>();
    }

    public <T extends Base> List<T> get(int index) {
        if (index == -1) {
            return (List<T>) defaultList;
        } else {
            return lists.get(index);
        }

    }

    @Test
    public void testPutAndGet() {
        List<Sub> list = new LinkedList<Sub>();
        this.put(list);
        List<Sub> result = this.get(-1);
        List<Sub> result2 = this.<Sub> get(0);
    }
}
