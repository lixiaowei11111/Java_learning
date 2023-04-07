@Resource("hello")
public class Hello {
    @Inject
    int t;

    @PostConstructor
    public void Hello(@Param String name) {
        System.out.println(name);
    }

    @Override
    public String toString() {
        return "Hello"
    }
}
