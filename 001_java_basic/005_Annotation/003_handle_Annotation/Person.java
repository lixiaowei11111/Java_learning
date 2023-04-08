@Report(type = 1)
public class Person {
    @Range(max = 20,min = 10)
    public String name;

    @Range(max = 50)
    public String city;
    
}
