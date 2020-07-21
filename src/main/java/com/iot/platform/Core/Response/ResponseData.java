package com.iot.platform.Core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.iot.platform.Util.ExeptionUtil;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseData<T> {

    public static <U> ResponseData<U> success(U data) {
        return new ResponseData<U>(data, "", new ArrayList<>(), HttpStatus.OK);
    }

    public static <U> ResponseData<U> success() {
        return success(null);
    }

    private static <U> ResponseData<U> internalError(HttpStatus statusCode, String message, List<String> messages) {
        return new ResponseData<U>(null, message, messages, statusCode);
    }

    public static <U> ResponseData<U> error(List<String> messages) {
        return internalError(HttpStatus.BAD_REQUEST, String.join(", ", messages), messages);
    }

    public static <U> ResponseData<U> error(String message) {
        return internalError(HttpStatus.BAD_REQUEST, message, Arrays.asList(message));
    }

    public static <U> ResponseData<U> error(String message, List<String> messages) {
        return internalError(HttpStatus.BAD_REQUEST, message, messages);
    }

    public static <U> ResponseData<U> error() {
        return error("{error.error}");
    }

    public static <U> ResponseData<U> notFound() {
        return notFound("{error.notFound}");
    }

    public static <U> ResponseData<U> notFound(List<String> messages) {
        return internalError(HttpStatus.NOT_FOUND, String.join(", ", messages), messages);
    }

    public static <U> ResponseData<U> notFound(String message) {
        return internalError(HttpStatus.NOT_FOUND, message, Arrays.asList(message));
    }

    public static <U> ResponseData<U> notFound(String message, List<String> messages) {
        return internalError(HttpStatus.NOT_FOUND, message, messages);
    }

    public static <U> ResponseData<U> forbidden(List<String> messages) {
        return internalError(HttpStatus.FORBIDDEN, String.join(", ", messages), messages);
    }

    public static <U> ResponseData<U> forbidden(String message) {
        return internalError(HttpStatus.FORBIDDEN, message, Arrays.asList(message));
    }

    public static <U> ResponseData<U> forbidden(String message, List<String> messages) {
        return internalError(HttpStatus.FORBIDDEN, message, messages);
    }

    public static <U> ResponseData<U> forbidden() {
        return forbidden("{error.forbidden}");
    }

    public static <U> ResponseData<U> unauthorized(List<String> messages) {
        return internalError(HttpStatus.UNAUTHORIZED, String.join(", ", messages), messages);
    }

    public static <U> ResponseData<U> unauthorized(String message) {
        return internalError(HttpStatus.UNAUTHORIZED, message, Arrays.asList(message));
    }

    public static <U> ResponseData<U> unauthorized(String message, List<String> messages) {
        return internalError(HttpStatus.UNAUTHORIZED, message, messages);
    }

    public static <U> ResponseData<U> unauthorized() {
        return unauthorized("{error.unauthorized}");
    }

    public static <U> ResponseData<U> invalidParam(BindingResult bindingResult) {
        return internalError(HttpStatus.BAD_REQUEST, "{invalid.data}", ExeptionUtil.arg(bindingResult));
    }

    public static <U> ResponseData<U> invalidParam(Set<ConstraintViolation<U>> violations) {
        return internalError(HttpStatus.BAD_REQUEST, "{invalid.data}", ExeptionUtil.arg(violations));
    }

    public static <U> ResponseData<U> fallbackError(HttpStatus httpStatus, List<String> messages) {
        return internalError(httpStatus, "{fallback}", messages);
    }

    public static <U> ResponseData<U> fallbackError(HttpStatus httpStatus) {
        return internalError(httpStatus, "{fallback}", Arrays.asList(""));
    }

    public static <U> ResponseData<U> from(ResponseData<?> origin) {
        return new ResponseData<U>(null, origin.getMessage(), origin.getMessages(), origin.getStatusCode());
    }

    T data;
    String message;
    List<String> messages;

    @ApiModelProperty(hidden = true)
    HttpStatus statusCode;

    public Boolean getStatus() {
        return this.getStatusCode().is2xxSuccessful();
    }

}