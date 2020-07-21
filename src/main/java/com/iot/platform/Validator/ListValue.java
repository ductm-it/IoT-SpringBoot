package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { ListValue.ListValueValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

    String[] param();

    String message()

    default "{com.iot.platform.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class ListValueValidator implements ConstraintValidator<ListValue, Object> {

        private Set<String> set;

        @Override
        public void initialize(ListValue constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.set = Arrays.asList(constraintAnnotation.param()).stream().collect(Collectors.toSet());
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null)
                return true;

            if (this.set.contains(value.toString()))
                return true;
            return false;
        }
    }

}