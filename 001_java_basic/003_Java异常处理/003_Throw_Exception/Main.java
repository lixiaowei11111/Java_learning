public class Main {
    public static void main(String[] args) {
        try {
            // process1(); // 异常的传播
            // process4(null); // 异常的类型转换
            // process5(); catch语句中抛出的异常 不会影响finally正常的语句执行
            // process6();finally 抛出异常后,会把catch语句throw的异常屏蔽掉
            process7();
        } catch (Exception e) {
            e.printStackTrace();// 打印异常堆栈信息
        }
        ;

    }

    static void process1() {
        process2();
    }

    private static void process2() {
        Integer.parseInt(null);// 会抛出NumberFormatException
    }

    private static void process3(String s) {
        if (s == null) {
            NullPointerException e = new NullPointerException();
            // throw e;//
            throw new NullPointerException();
        }
    }

    private static void process4(String s) {
        try {
            process3(s);
        } catch (NullPointerException e) {
            // 转换错误类型
            // throw new IllegalArgumentException();
            // 为了能追踪到完整的异常栈，在构造异常的时候，把原始的Exception实例传进去，新的Exception就可以持有原始Exception信息。对上述代码改进如下：
            throw new IllegalArgumentException(e);
        }
        ;
    }

    private static void process5() {
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("caught");
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
        }
    }

    private static void process6() {
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("caught");
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
            throw new IllegalArgumentException();
        }
    }

    private static void process7() throws Exception {
        Exception origin = null;
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            origin= e;
            throw e;
        } finally {
            Exception e=new IllegalArgumentException();
            if(origin!=null){
                e.addSuppressed(origin);
            }
            throw e;
        }
    }
}
