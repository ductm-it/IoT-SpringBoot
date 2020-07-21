package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.web.multipart.MultipartFile;

@Documented
@Constraint(validatedBy = { MimeType.MimeTypeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MimeType {

    String[] param();

    String message() default "{com.iot.platform.MimeType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class MimeTypeValidator implements ConstraintValidator<MimeType, MultipartFile> {

        private Set<String> set;

        @Override
        public void initialize(MimeType constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.set = Arrays.asList(constraintAnnotation.param()).stream().collect(Collectors.toSet());
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            if (value == null)
                return true;
            
            if (this.set.contains(value.getContentType())) return true;
            return false;
        }
    }

}