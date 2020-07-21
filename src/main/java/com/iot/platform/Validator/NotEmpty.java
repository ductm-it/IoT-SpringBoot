package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { NotEmpty.NotEmptyValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String message() default "{com.iot.platform.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null)
                return false;

            int size = 0;
            if (value instanceof CharSequence) {
                size = ((CharSequence) value).length();
            }
            if (value instanceof Collection<?>) {
                size = ((Collection<?>) value).size();
            }
            if (value instanceof Map<?, ?>) {
                size = ((Map<?, ?>) value).size();
            }
            if (value.getClass().isArray()) {
                size = Array.getLength(value);
            }

            if (size == 0)
                return false;
            return true;
        }
    }

}