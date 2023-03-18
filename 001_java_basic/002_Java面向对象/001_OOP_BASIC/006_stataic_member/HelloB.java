public class HelloB extends HelloA {

    public HelloB() {
        System.out.println("Hellob");
    }

    {
        System.out.println("BBB");
    }

    static {
        System.out.println("BBB static");
    }

    public static void main(String[] args) {
        new HelloB();
    }
}