public class Main {
    public static void main(String[] args){
        // Person p=new Person();// compiler error 'Person' 为 abstract；无法实例化
        Person p=new Student();
        if(p instanceof Student){
            p.run();// 'Student.run'
        }

        // 计算税率
        Income sTax=new Salary();
        Income aTax=new ArticleIncome();
        System.out.println("一共需要缴纳税款为: "+ (sTax.getTax(6000)+aTax.getTax(10000)));
    }
}

abstract class Person{
    public abstract  void run();
    //abstract 不能有 {}, 相当于 只是声明了这个函数, 但是未定义,需要由子类来定义
}

// 继承 abstract类的 子类 必须声明 Base类中的抽象方法, 或者本身也为abstract 类
//class Student extends Person{
    // compiler error 类 "Student" 必须声明为抽象，或为实现 "Person" 中的抽象方法 "run()"
// }

class Student extends Person{
    @Override
    public void run(){
        System.out.println("\'Student.run\'");
    }
}

// 用抽象类 给 一个有工资收入 和 稿费收入的小伙伴算税
// 稿费税率为 14%;
// 工资税率 超过 5000 到8000的部分为 3%
abstract class Income{
    public abstract double getTax(double come);
}

class Salary extends Income{
    @Override
    public double getTax(double income){
        if(income>5000){
            return (income-5000)*0.03;
        }
        return 0;
    };
}

class ArticleIncome extends Income{
    @Override
    public double getTax(double articleIncome){
        return  articleIncome*0.14;
    }
}