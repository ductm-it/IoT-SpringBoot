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
@Constraint(validatedBy = { Phone.PhoneValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String param() default  "^0[0-9]{9}$";
    String message() default "{com.iot.platform.Phone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class PhoneValidator implements ConstraintValidator<Phone, String> {

        private Pattern pattern;

        @Override
        public void initialize(Phone constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.pattern = Pattern.compile(constraintAnnotation.param());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            return this.pattern.matcher(value).matches();
        }
    }

}