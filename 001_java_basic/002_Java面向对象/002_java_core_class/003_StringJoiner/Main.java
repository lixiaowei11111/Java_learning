import java.util.StringJoiner;

public class Main {
    public static void main(String[] args){
        String names[]={"Zhang","Li","Yu"};
        var sb1=new StringBuilder();//var 关键字用于自动类型推导
        StringJoiner sj1=new StringJoiner(", ","Hello ","!");// 三个参数 分隔符,开头,结尾
        sb1.append("Hello ");
        for (String name:names
             ) {
            sb1.append(name).append(", ");
            sj1.add(name);
        }

        // 去掉最后的 ", "
        sb1.delete(sb1.length()-2,sb1.length());
        sb1.append("!");
        System.out.println(sb1.toString());// Hello Zhang, Li, Yu!
        System.out.println(sj1);// Hello Zhang, Li, Yu!

        // 练习
        String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String select = buildSelectSql(table, fields);
        System.out.println(select);
        System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "测试成功" : "测试失败");
    }

    private static String buildSelectSql(String table, String[] fields) {
        var sj=new StringJoiner(", ","SELECT "," FROM "+table);
        for (String field:fields
             ) {
            sj.add(field);
        };
        System.out.println(sj);
        return sj.toString();
    }
}
