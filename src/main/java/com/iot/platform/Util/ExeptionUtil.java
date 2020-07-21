package com.iot.platform.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ExeptionUtil {

    public static List<String> arg(BindingResult bindingResult) {

        List<String> errors = bindingResult.getFieldErrors().stream().map(t -> {
            return t.getField() + ": " + t.getDefaultMessage();
        }).collect(Collectors.toList());

        ObjectError objectError = bindingResult.getGlobalError();
        if (objectError != null) {
            errors.add("root" + ": " + objectError.getDefaultMessage());
        }

        return errors;
    }

    public static <U> List<String> arg(Set<ConstraintViolation<U>> violations) {
        List<String> errors = new ArrayList<>();
        errors.addAll(violations.stream().map(t -> {
            return t.getPropertyPath() + ": " + t.getMessage();
        }).collect(Collectors.toList()));
        return null;
    }

}