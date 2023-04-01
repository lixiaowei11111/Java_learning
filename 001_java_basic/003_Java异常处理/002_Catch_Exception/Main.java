import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            process1();
            process2();
            process3();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Bad Input");
        } catch (Exception e) {
            System.out.println("Unknown error");
        } finally {
            System.out.println("END");
        }
    }

    void process(String file) throws IOException {
        try {
        } finally {
            System.out.println("END");
        }
    }
}
