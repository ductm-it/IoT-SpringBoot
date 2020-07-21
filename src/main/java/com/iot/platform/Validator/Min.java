package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { Min.MinValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Min {

    long param();

    String message() default "{com.iot.platform.Min.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @SuppressWarnings("all")
    public class MinValidator implements ConstraintValidator<Min, Comparable> {

        private long param;

        @Override
        public void initialize(Min constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.param = constraintAnnotation.param();
        }

        @Override
        public boolean isValid(Comparable value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            Class<? extends Comparable> clazz = value.getClass();

            if (clazz.isAssignableFrom(BigDecimal.class)) {
                return value.compareTo(BigDecimal.valueOf(this.param)) >= 0 ? true : false;
            }
            if (clazz.isAssignableFrom(BigInteger.class)) {
                return value.compareTo(BigInteger.valueOf(this.param)) >= 0 ? true : false;
            }

            Object _value = null;

            if (clazz.isAssignableFrom(Long.class)) {
                _value = this.param;
            }
            if (clazz.isAssignableFrom(Integer.class)) {
                _value = (int) (long) this.param;
            }
            if (clazz.isAssignableFrom(Short.class)) {
                _value = (short) (long) this.param;
            }
            if (clazz.isAssignableFrom(Double.class)) {
                _value = (double) (long) this.param;
            }
            if (value.compareTo(_value) >= 0)
                return true;

            return false;
        }
    }

}