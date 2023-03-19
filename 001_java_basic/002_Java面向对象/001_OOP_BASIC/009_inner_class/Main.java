
import static java.lang.System.*;

// 暴露 System所有的 static 属性 和方法;
public class Main {
    public static void main(String[] args) {
        Outer o = new Outer("lcw");// 实例化一个 Outer class
        Outer.Inner i = o.new Inner();// 实例化 Inner class Inner实例不能被单独创建
        i.hello();// Inner+ Outer.this.namelcw
        Outer.Inner.InnerInner ii = i.new InnerInner();// 创建 InnerInner 实例
        ii.hello();// Inner+ Outer.this.nameinnerlcw0

        // 匿名类的创建
        o.asyncHello();// Hello,lcw
        // static nested class 是一个独立的class,不需要依赖于 Outer的实例来创建
        Outer.StaticNested sn = new Outer.StaticNested();
        sn.hello();// 不再依赖于Outer的实例,所以也不能用Outer.this来访问属性,但是可以访问 static 成员OUTERNAME

    }
}


class Outer {
    private String name;
    protected int age;

    public Outer(String name) {
        this.name = name;
    }

    ;
    private static String outerName = "OUTERNAME";

    private void hello() {
        out.println("outer Hello");
    }

    public class Inner {
        private String inner = "inner";

        public void hello() {
            // 访问 Outer class的 所有的属性 private protected public package
            out.println("Inner+ Outer.this.name" + Outer.this.name + Outer.this.age);
        }

        public class InnerInner {
            public void hello() {
                // 访问 Outer class的 所有的属性 private protected public package
                // 访问 Inner class 的所有属性
                out.println("Inner+ Outer.this.name" + Inner.this.inner + Outer.this.name + Outer.this.age);
            }
        }
    }

    ;

    static class StaticNested {// static nested class 是一个独立的class, 不再依赖于 Outer的实例

        void hello() {
            out.println("不再依赖于Outer的实例,所以也不能用Outer.this来访问属性,但是可以访问 static 成员" + Outer.outerName);
        }
    }
    void asyncHello(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                out.println("Hello,"+Outer.this.name);
            }
        };
        new Thread(r).start();
    };

}