package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.iot.platform.Util.EntityUtil;

@Documented
@Constraint(validatedBy = { TableName.TableNameValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {

    String param();

    String message() default "{com.iot.platform.TableName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class TableNameValidator implements ConstraintValidator<TableName, String> {

        private Set<String> tableSet;

        @Override
        public void initialize(TableName constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
            this.tableSet = EntityUtil.getAllTables(constraintAnnotation.param()).stream().collect(Collectors.toSet());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return this.tableSet.contains(value);
        }
    }

}