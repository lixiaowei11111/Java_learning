import java.beans.*;// 引入Introspector
import static java.lang.System.*;// 引入System上的所有静态属性
public class Main {
    public static void main(String[] args) throws Exception{
        BeanInfo info=Introspector.getBeanInfo(Student.class);// Student上的class为静态属性
        for (PropertyDescriptor pd:info.getPropertyDescriptors()
             ) {
            out.println(pd.getName());
            out.println(""+pd.getReadMethod());
            out.println(""+pd.getWriteMethod()   );
        }
    }
}



class Student {
    private String name;
    private int age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChild() {
        return this.age <= 6;
    }
}
