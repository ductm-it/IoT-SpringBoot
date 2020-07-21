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
@Constraint(validatedBy = { EntityFieldName.EntityFieldNameValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityFieldName {

    String param() default "^([a-zA-Z0-9_]+)$";

    String message() default "{com.iot.platform.EntityFieldName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class EntityFieldNameValidator implements ConstraintValidator<EntityFieldName, String> {

        private Pattern pattern;

        @Override
        public void initialize(EntityFieldName constraintAnnotation) {
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