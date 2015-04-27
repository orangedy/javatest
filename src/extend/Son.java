package extend;


public class Son extends Father {

    public Son() {
        super("");
    }

//    public Son(String prop) {
//        super(prop);
//    }

    public static void main(String[] args) {
        Outer.Inner in = new Outer.Inner();
    }
}
