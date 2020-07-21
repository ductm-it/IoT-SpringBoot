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
@Constraint(validatedBy = { Password.PasswordValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String param() default "^.{8,}$";

    String message() default "{com.iot.platform.Password.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class PasswordValidator implements ConstraintValidator<Password, String> {

        private Pattern pattern;

        @Override
        public void initialize(Password constraintAnnotation) {
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