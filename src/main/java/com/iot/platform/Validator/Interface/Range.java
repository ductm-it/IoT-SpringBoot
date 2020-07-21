package com.iot.platform.Validator.Interface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.iot.platform.Interface.Validator.RangeInterface;

@Documented
@Constraint(validatedBy = { Range.RangeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    String message() default "{com.iot.platform.Range.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @SuppressWarnings("all")
    public class RangeValidator implements ConstraintValidator<Range, RangeInterface> {

        @Override
        public boolean isValid(RangeInterface value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            Comparable from = value.getFrom();
            Comparable to = value.getTo();

            if (from == null || to == null)
                return true;
            return from.compareTo(to) <= 0 ? true : false;
        }
    }

}