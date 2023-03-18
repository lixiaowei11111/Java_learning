package hello;

// 或者
import hello.Person;
import my.arr.*;
// 同一个package内才能访问到
public class Main {
    public static void main(String[] args){
        Hello h=new Hello();
        h.hello();// Hello中的hello==>friendly
        Person p=new Person();
        Arrays a=new Arrays();
    }
}
