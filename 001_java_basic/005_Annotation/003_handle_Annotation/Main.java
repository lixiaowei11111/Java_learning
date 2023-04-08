public class Main {
    public static void main(String[] args){
        boolean b1=Person.class.isAnnotationPresent(Report.class);
        System.out.println(b1);// true

        Report report=Person.class.getAnnotation(Report.class);
        int type= report.type();
        String level= report.level();
        System.out.println(type+"\n"+level);// 1 info
    }
}
