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
@Constraint(validatedBy = { Regexp.RegexpValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Regexp {

    String param();

    String message() default "{com.iot.platform.Min.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class RegexpValidator implements ConstraintValidator<Regexp, String> {

        private Pattern param;

        @Override
        public void initialize(Regexp constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.param = Pattern.compile(constraintAnnotation.param());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            return this.param.matcher(value).matches();
        }
    }

}