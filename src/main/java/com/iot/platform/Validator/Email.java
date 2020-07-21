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
@Constraint(validatedBy = { Email.EmailValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String param() default "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
    String message() default "{com.iot.platform.Email.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class EmailValidator implements ConstraintValidator<Email, String> {

        private Pattern pattern;

        @Override
        public void initialize(Email constraintAnnotation) {
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