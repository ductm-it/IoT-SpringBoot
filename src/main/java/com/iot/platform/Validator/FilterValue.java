package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { FilterValue.FilterValueValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterValue {

    String param() default "^([a-zA-Z0-9 \\-_\\.]*)$";

    String message() default "{com.iot.platform.FilterValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class FilterValueValidator implements ConstraintValidator<FilterValue, Object> {

        private Pattern pattern;

        @Override
        public void initialize(FilterValue constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.pattern = Pattern.compile(constraintAnnotation.param());
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            return this.pattern.matcher(value.toString()).matches();
        }
    }

}