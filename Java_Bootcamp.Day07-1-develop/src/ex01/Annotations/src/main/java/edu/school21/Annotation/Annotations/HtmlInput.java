package edu.school21.Annotation.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface HtmlInput {
    String type();
    String name();
    String placeholder();
}
