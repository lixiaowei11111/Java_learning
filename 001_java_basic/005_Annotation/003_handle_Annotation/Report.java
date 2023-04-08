import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Reports.class)
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";

}
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface Reports{
    Report[] value();
}