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
@Constraint(validatedBy = { NotBlank.NotBlankValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    String message()

    default "{com.iot.platform.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {

        @Override
        public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
            if (charSequence == null) {
                return true;
            }
            return charSequence.toString().trim().length() > 0;
        }
    }
}