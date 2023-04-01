public class Main {
    public static void main(String[] args) {
        double x = Math.abs(-123.45);
        assert x >= 0;
        assert x >= 0 : "x must >= 0";// 增加断言消息
        System.out.println(x);
        int y=-1;
        assert y>=0:"y must >= 0";
    }

    void sort(int[] arr) {
        assert arr != null;// 不能使用断言
        if (arr == null) {
            throw new IllegalArgumentException("array cannot be null!");
        }
    }
}
