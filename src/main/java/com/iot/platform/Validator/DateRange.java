package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.iot.platform.Interface.Entity.DateRangeInterface;

@Documented
@Constraint(validatedBy = { DateRange.DateRangeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {

    String message() default "{com.iot.platform.DateRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class DateRangeValidator implements ConstraintValidator<DateRange, DateRangeInterface> {

        @Override
        public boolean isValid(DateRangeInterface value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            Date from = value.getFromDate();
            Date to = value.getToDate();

            if (from == null || to == null)
                return true;
            return from.compareTo(to) <= 0 ? true : false;
        }

    }

}