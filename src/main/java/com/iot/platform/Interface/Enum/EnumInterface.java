package com.iot.platform.Interface.Enum;

public interface EnumInterface {

    public static Object valueOf(String value, Class<?> clazz) {
        Object[] enums = clazz.getEnumConstants();
        for (Object e : enums) {
            if (e.toString().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
