import Report.*;
// 使用@Repeatable 元注解,注解了@Report 这个注解
@Report(type=1,level = "debug")
@Report(type=2,level = "warning")
public class Main {
    public static void main(String[] args) {

    }
}


