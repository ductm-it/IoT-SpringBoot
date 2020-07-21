package com.iot.platform.Interface.UI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This interface will ask client side to generate an input type checkbox.
 * 
 * <pre>
 *  &lt;input type="checkbox"/&gt;
 * </pre>
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InputBooleanInterface {

}