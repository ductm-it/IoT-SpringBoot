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
@Constraint(validatedBy = { Size.SizeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "{com.iot.platform.Size.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class SizeValidator implements ConstraintValidator<Size, Object> {

        private int max, min;

        @Override
        public void initialize(Size constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.max = constraintAnnotation.max();
            this.min = constraintAnnotation.min();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null)
                return true;

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

            if (size > max || size < min)
                return false;
            return true;
        }
    }

}