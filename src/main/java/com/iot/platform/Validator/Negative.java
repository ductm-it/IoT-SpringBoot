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
@Constraint(validatedBy = { Negative.NegativeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Negative {

    String message() default "{com.iot.platform.Negative.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @SuppressWarnings("all")
    public class NegativeValidator implements ConstraintValidator<Negative, Comparable> {

        @Override
        public boolean isValid(Comparable value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            Class<? extends Comparable> clazz = value.getClass();

            if (clazz.isAssignableFrom(BigDecimal.class)) {
                return value.compareTo(BigDecimal.valueOf(0)) < 0 ? true : false;
            }
            if (clazz.isAssignableFrom(BigInteger.class)) {
                return value.compareTo(BigInteger.valueOf(0)) < 0 ? true : false;
            }

            Object _value = null;

            if (clazz.isAssignableFrom(Long.class)) {
                _value = (long) 0;
            }
            if (clazz.isAssignableFrom(Short.class)) {
                _value = (short) 0;
            }
            if (clazz.isAssignableFrom(Byte.class)) {
                _value = (byte) 0;
            }
            if (clazz.isAssignableFrom(Double.class)) {
                _value = (double) (long) 0;
            }
            if (clazz.isAssignableFrom(Float.class)) {
                _value = (float) (long) 0;
            }
            _value = _value == null ? 0 : _value;
            if (value.compareTo(_value) < 0)
                return true;

            return false;
        }
    }

}