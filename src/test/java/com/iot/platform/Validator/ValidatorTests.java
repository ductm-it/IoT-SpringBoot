package com.iot.platform.Validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.iot.platform.Entity.DeviceEntity;

public class ValidatorTests {
    public static void main(String[] args) {

        DeviceEntity a = new DeviceEntity();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DeviceEntity>> violations = validator.validate(a);

        System.out.println("@@@");
        System.out.println(violations);
        System.out.println("@@@");
    }

}