package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { AssertTrue.NotNullValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertTrue {

    String message() default "{com.iot.platform.AssertTrue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class NotNullValidator implements ConstraintValidator<AssertTrue, Boolean> {

        @Override
        public boolean isValid(Boolean value, ConstraintValidatorContext context) {
            if (value == null) return true;
            return value == true ? true : false;
        }
    }

}