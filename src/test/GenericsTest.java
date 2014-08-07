package test;

public class GenericsTest<T> {

    private T t;

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

    public static void main(String[] args) {
        GenericsTest<String> test = new GenericsTest<String>();
        test.variParam("1", "2", "3");
    }
}
