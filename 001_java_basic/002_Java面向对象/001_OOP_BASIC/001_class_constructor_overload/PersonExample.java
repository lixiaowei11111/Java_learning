

public class PersonExample {
    PersonExample(){};// 默认构造
    PersonExample(String name,int age){
        this.name=name;
        this.age=age;
    }
    private String name;
    private int age;
    public String getName(){
        return  name;
    }
    public int getAge(){
        return  age;
    }
}
