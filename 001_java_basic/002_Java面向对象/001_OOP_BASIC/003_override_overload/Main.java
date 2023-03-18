public class Main {
    public static void main(String[] args){
        Person p=new Student();
        p.run();// Student.run()

        // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
        Income incomes[]=new Income[]{
                new Income(3000),
                new Salary(7500),
                new StateCouncilSpecialAllowance(15000)
        };
        System.out.println("这个小伙需要交的税为: "+totalTax(incomes));
    };
    // 4. 现在，我们要编写一个报税的财务软件，对于一个人的所有收入进行报税，可以这么写：
    public static double totalTax(Income... incomes){
        double total=0;
        for (Income income :incomes) {
            total=total+income.getTax();
        }
        return  total;
    }
};

final class Person{
    // 用final 修饰的class 将不能被 extends
    private String name;
    private int age;
    public final  void run(){// 子类 将不能 override 这个方法
        System.out.println("Person.run()");
    }
    // 重写 继承自 Object的 toString func
    @Override
    public String toString(){
        return "Person.name = "+name;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Person){
            Person p=(Person) o;
            return  this.name.equals(p.name);
        };
        return false;
    };
    @Override
    public int hashCode(){
        return this.name.hashCode();
        // 这个哈希码的作用是确定该对象在哈希表中的索引位置。
        // hashCode () 定义在JDK的Object.java中，这就意味着Java中的任何类都包含有hashCode () 函数。
    }
}

class Student extends Person{
    // 不能继承 被 final 修饰的 class
    @Override
    public void run(){//complier error base 类 使用了 final 修饰的方法不能被更改
        // System.out.println("Student.run()");
        // 调用base类的run方法
        super.run();
    }
};

// 多态

//1. 假设我们定义一种收入，需要给它报税，那么先定义一个Income类：
class Income{
    public Income(double income){
        this.income=income;
    };
    protected double income;
    public double getTax(){
        return  this.income*0.1;// 10%的税率
    }
}

// 2. 对于工资收入，可以减去一个基数，那么我们可以从Income派生出SalaryIncome，并覆写getTax()：
class Salary extends Income{
    public Salary(double income) {
        super(income);
    }

    @Override
    public double getTax(){
        if(income<5000){
            return 0;
        }
        return (income-5000)*0.2;
    }
}
// 3. 如果你享受国务院特殊津贴，那么按照规定，可以全部免税：
class StateCouncilSpecialAllowance extends Income{
    public StateCouncilSpecialAllowance(double income) {
        super(income);
    }

    @Override
    public double getTax(){
        return 0;
    }
}
