package test;

public enum EnumTest {
    Value1(1, "value1"),
    Value2(2, "value2");

    private int value;
    private String description;

    EnumTest(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static void main(String[] args) {
        System.out.println(Value1.value);
        System.out.println(Value1.description);
    }
}
