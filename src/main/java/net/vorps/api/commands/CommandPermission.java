package net.vorps.api.commands;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandPermissions.class)
public @interface CommandPermission {
    String value() default "";
    boolean console() default true;
}
