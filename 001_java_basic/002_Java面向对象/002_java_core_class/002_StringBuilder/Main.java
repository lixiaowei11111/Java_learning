public class Main {
    public static void main(String[] args){
        StringBuilder sb1=new StringBuilder(1024);
        for (int i=0;i<100;i++){
            // 链式调用, 因为 StringBuilder的append方法返回的this指针(的引用)
            sb1.append("\n").append(",").append(i);
        }
        String s1=sb1.toString();
        System.out.println(s1);

        Counter c1=new Counter();
        c1.add(10).inc().add(10);
        System.out.println(c1.value());// 21

        String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String insert = buildInsertSql(table, fields);
        System.out.println(insert);
        String s = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
        System.out.println(s.equals(insert) ? "测试成功" : "测试失败");
    }

    private static String buildInsertSql(String table, String[] fields) {
        System.out.println(fields.length+"==>field,length");
        StringBuilder initStr=new StringBuilder("INSERT INTO ");
        initStr.append(table+" (");
        for (int i=0;i< fields.length;i++){
            initStr.append(fields[i]);
            if(i< fields.length-1){
                initStr.append(", ");
            };
        }
        initStr.append(") VALUES (");
        for (int i=0;i< fields.length;i++){
            initStr.append("?");
            if(i< fields.length-1){
                initStr.append(", ");
            };
        }
        initStr.append(")");
        return initStr.toString();
    }
}

// 仿照 StringBuilder 做一个链式调用的类

class Counter{
    private int sum=0;
    // 返回当前引用
    public Counter  add(int n){
        sum=sum+n;
        return this;
    }
    // 自增
    public Counter inc(){
        sum++;
        return this;
    }
    public int value(){
        return sum;// this.sum 省略了this
    }
}