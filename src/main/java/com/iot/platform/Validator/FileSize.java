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

import org.springframework.web.multipart.MultipartFile;

@Documented
@Constraint(validatedBy = { FileSize.FileSizeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSize {

    long param();

    String message() default "{com.iot.platform.FileSize.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

        private long fileSize;

        @Override
        public void initialize(FileSize constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.fileSize = constraintAnnotation.param();
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            if (value == null)
                return true;

            if (this.fileSize >= value.getSize())
                return true;
            return false;
        }
    }

}