package com.una.embyhub.config.job;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduledTaskMeta {
   String name();

   String remark() default "";

   boolean hidden() default false;

   boolean locked() default false;
}
