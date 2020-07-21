package com.iot.platform.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.Calendar;
import java.util.Date;
import java.time.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { Past.PastValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Past {

    String message() default "{com.iot.platform.Past.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @SuppressWarnings("all")
    public class PastValidator implements ConstraintValidator<Past, Object> {

        @Override
        public boolean isValid(final Object value, final ConstraintValidatorContext context) {
            if (value == null)
                return true;
            final Class<?> clazz = value.getClass();
            if (clazz.isAssignableFrom(Date.class)) {
                return new Date().compareTo((Date) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(Calendar.class)) {
                return Calendar.getInstance().compareTo((Calendar) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(Instant.class)) {
                return Instant.now().compareTo((Instant) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(LocalDate.class)) {
                return LocalDate.now().compareTo((LocalDate) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(LocalDateTime.class)) {
                return LocalDateTime.now().compareTo((LocalDateTime) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(LocalTime.class)) {
                return LocalTime.now().compareTo((LocalTime) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(MonthDay.class)) {
                return MonthDay.now().compareTo((MonthDay) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(OffsetDateTime.class)) {
                return OffsetDateTime.now().compareTo((OffsetDateTime) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(OffsetTime.class)) {
                return OffsetTime.now().compareTo((OffsetTime) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(Year.class)) {
                return Year.now().compareTo((Year) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(YearMonth.class)) {
                return YearMonth.now().compareTo((YearMonth) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(ZonedDateTime.class)) {
                return ZonedDateTime.now().compareTo((ZonedDateTime) value) > 0 ? true : false;
            }

            if (clazz.isAssignableFrom(HijrahDate.class)) {
                return HijrahDate.now().compareTo((HijrahDate) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(JapaneseDate.class)) {
                return JapaneseDate.now().compareTo((JapaneseDate) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(MinguoDate.class)) {
                return MinguoDate.now().compareTo((MinguoDate) value) > 0 ? true : false;
            }
            if (clazz.isAssignableFrom(ThaiBuddhistDate.class)) {
                return ThaiBuddhistDate.now().compareTo((ThaiBuddhistDate) value) > 0 ? true : false;
            }

            return false;
        }
    }

}