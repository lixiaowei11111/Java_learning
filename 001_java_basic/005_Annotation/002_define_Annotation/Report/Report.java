package Report;

import java.lang.annotation.*;

//@Target({ElementType.METHOD, ElementType.FIELD,ElementType.TYPE})
//// @Traget 自定义注解 能够被在哪些位置被应用
//
//@Retention(RetentionPolicy.RUNTIME)
//// @Retetion 自定义注解在哪些生命周期被使用
////如果@Retention不存在，则该Annotation默认为CLASS。因为通常我们自定义的Annotation都是RUNTIME，
////所以，务必要加上@Retention(RetentionPolicy.RUNTIME)这个元注解：
//
//public @interface Report {
//    int type() default 0;
//
//    String level() default "info";
//
//    String value() default "";
//}

@Repeatable(Reports.class)
@Target(ElementType.TYPE)
@Inherited
public @interface Report {
    int type() default 0;

    String level() default "info";

    String value() default "";
}

@Target(ElementType.TYPE)
@Inherited
@interface Reports {
    Report[] value();
}