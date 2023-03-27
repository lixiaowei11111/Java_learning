public class Main {
    public static void main(String[] args) {
        int day = 0;
        if (day == Weekday.SUN || day == Weekday.SAT) {
            // TODO
        }
        String s = Weekday2.MON.name();
        System.out.println(s);// MON
        int n = Weekday2.FRI.ordinal();
        System.out.println(n);

        Weekday2 wk2 = Weekday2.MON;
        if (wk2.dayValue == 6 || wk2.dayValue == 0) {
            System.out.println("" + wk2 + "at home");
        } else {
            System.out.println("" + wk2 + "on work");
        }
        ;

        switch (wk2) {
            case SUN:
            case MON:
            case TUE:
            case WED:
            case SAT:
            case FRI:
                System.out.println("Today is" + day + ". work at office");
                break;
            default:
                throw new RuntimeException("cannot process " + day);
        }
    }
}

class Weekday {
    // Java 中通过 static final 来定义常量
    public static final int SUN = 0;
    public static final int MON = 1;
    public static final int TUS = 2;
    public static final int WED = 3;
    public static final int THU = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
}

enum Color {
    RED, GREEN, BLUE;
}

enum Weekday2 {
    SUN(0, "星期日"), MON(1, "星期一"), TUE(2, "星期二"), WED(3, "星期三"), THU(4, "星期四"), FRI(5, "星期五"), SAT(6, "星期六");
    public final int dayValue;
    public final String chinese;

    private Weekday2(int dayValue, String chinese) {
        this.dayValue = dayValue;
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return this.chinese;
    }
}
