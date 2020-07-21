package com.iot.platform.Util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.iot.platform.Core.Response.ResponseFilter;
import com.iot.platform.Core.Response.UiConfig;
import com.iot.platform.Core.Response.Validator;
import com.iot.platform.Entity.BaseEntity;
import com.iot.platform.Enum.System.ServerActionEnum;
import com.iot.platform.Interface.Config.SortInterface;
import com.iot.platform.Interface.Enum.EnumInterface;
import com.iot.platform.Interface.Function.ConvertFilterDataFunctionInterface;
import com.iot.platform.Interface.System.EntityPermissionInterface;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class InputConfig {

    private String name;
    private String type;
    private Annotation mainAnnotation;
    private List<Annotation> annotations;

    public InputConfig(String name, List<Annotation> annotations) {
        this.name = name;
        this.annotations = annotations;
    }

}

public class EntityUtil {

    public static List<Field> getAllFields(Class<?> type) {
        Class<?> t = type;
        List<Field> fields = new ArrayList<>();
        while (t != null && !t.getCanonicalName().equals(BaseEntity.class.getCanonicalName())) {
            fields.addAll(Arrays.stream(t.getDeclaredFields()).collect(Collectors.toList()));
            t = t.getSuperclass();
        }
        return fields;
    }

    public static List<InputConfig> getInputs(Class<?> type) {
        return getAllFields(type).stream().map(e -> {
            return new InputConfig(e.getName(), Arrays.asList(e.getAnnotations()));
        }).filter(e -> {
            for (Annotation annotation : e.getAnnotations()) {
                if (annotation.annotationType().getCanonicalName().startsWith("com.iot.platform.Interface.UI")) {
                    e.setType(annotation.annotationType().getSimpleName());
                    e.setMainAnnotation(annotation);
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    private static Object getParam(Annotation annotation, String method) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        InvocationHandler invocationHandler = Proxy.getInvocationHandler((Proxy) annotation);
        Object value = null;
        try {
            Method paramMethod = aClass.getDeclaredMethod(method);
            value = invocationHandler.invoke(invocationHandler, paramMethod, null);
        } catch (Throwable e1) {
        }
        return value;
    }

    private static List<Class<?>> getAllEntites(String packageName) {
        List<Class<?>> clazzs = new ArrayList<>();
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false);
        // add include filters which matches all the classes
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        final Set<BeanDefinition> classes = provider.findCandidateComponents(packageName);

        for (BeanDefinition bean : classes) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                clazzs.add(clazz);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return clazzs;
    }

    public static String getTableName(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null) {
            return null;
        }
        return (String) getParam(annotation, "name");
    }

    public static String[] getPermissions(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(EntityPermissionInterface.class);
        if (annotation == null) {
            return null;
        }
        return (String[]) getParam(annotation, "permissions");
    }

    public static String getTablePrimaryKey(Class<?> clazz) {
        if (clazz.getAnnotation(Entity.class) == null) {
            return null;
        }
        Optional<String> optional = getAllFields(clazz).stream().map(e -> {
            if (e.getAnnotation(Id.class) != null) {
                return e.getName();
            }
            return null;
        }).filter(e -> e != null).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public static List<String> getAllTables(String packageName) {
        List<Class<?>> clazzs = getAllEntites(packageName);
        List<String> tables = new ArrayList<>();
        for (Class<?> clazz : clazzs) {
            String tableName = getTableName(clazz);
            if (tableName == null) {
                continue;
            }
            tables.add(tableName);
        }
        return tables;
    }

    public static List<String> getAllPermissions(String packageName) {
        List<Class<?>> clazzs = getAllEntites(packageName);
        List<String> tables = new ArrayList<>();
        for (Class<?> clazz : clazzs) {
            String[] permissions = getPermissions(clazz);
            if (permissions == null || permissions.length == 0) {
                continue;
            }
            tables.addAll(Arrays.asList(permissions));
        }
        return tables.stream().sorted().distinct().collect(Collectors.toList());
    }

    /**
     * 
     * This function to support your CMS to pass the entity config to end client.
     * 
     * @param type an entity class.
     * @return The list of {@link com.iot.platform.Util.UiConfig UiConfig}.
     */
    public static List<UiConfig> getUiConfigs(Class<?> type) {
        return getInputs(type).stream().map(e -> {
            UiConfig uiConfig = new UiConfig(e.getName(), e.getType().substring(5, e.getType().length() - 9));
            Map<String, Object> param = uiConfig.getParam();
            switch (uiConfig.getType()) {

                case "SelectFromEnum":
                case "RadioGroupFromEnum":
                    Class<?> clazz = (Class<?>) getParam(e.getMainAnnotation(), "clazz");
                    param.put("param", Arrays.asList(clazz.getEnumConstants()).stream().map(t -> {
                        return new ResponseFilter(((Enum<?>) t).name(), ((Enum<?>) t).toString());
                    }).collect(Collectors.toList()));
                    break;
                case "UserRole":
                    param.put("actions", Arrays.asList(ServerActionEnum.values()).stream().map(t -> {
                        return new ResponseFilter(t.name(), t.getData().toString());
                    }).collect(Collectors.toList()));
                    String packageName = (String) getParam(e.getMainAnnotation(), "packageName");
                    param.put("tables", getAllPermissions(packageName));
                    break;
                case "RadioGroup":
                case "SelectStatic":
                    String[] values = (String[]) getParam(e.getMainAnnotation(), "values");
                    param.put("param", values);
                    break;

                case "SelectFromTable":
                    param.put("table", getParam(e.getMainAnnotation(), "table"));
                    break;

                case "SelectFromUrl":
                    param.put("filterUrl", getParam(e.getMainAnnotation(), "filterUrl"));
                    param.put("getNameUrl", getParam(e.getMainAnnotation(), "getNameUrl"));
                    break;

                default:
                    break;
            }

            List<Validator> validators = uiConfig.getValidators();
            for (Annotation annotation : e.getAnnotations()) {
                if (annotation.annotationType().getCanonicalName().startsWith("com.iot.platform.Validator")) {
                    Validator validator = new Validator();
                    validator.setType(annotation.annotationType().getSimpleName());
                    validator.setMessage((String) getParam(annotation, "message"));
                    Map<String, Object> vParam = validator.getParam();
                    switch (validator.getType()) {

                        case "Size":
                            vParam.put("min", getParam(annotation, "min"));
                            vParam.put("max", getParam(annotation, "max"));
                            break;
                        case "Username":
                        case "Min":
                        case "Max":
                        case "MimeType":
                        case "SqlId":
                        case "Phone":
                        case "Email":
                        case "EntityFieldName":
                        case "FileSize":
                        case "FilterValue":
                        case "Regexp":
                        case "ListValue":
                        case "Password":
                        case "TableName":
                            vParam.put("param", getParam(annotation, "param"));
                            break;

                        default:
                            break;
                    }

                    validators.add(validator);
                }
            }

            return uiConfig;
        }).collect(Collectors.toList());
    }

    public static Map<String, ConvertFilterDataFunctionInterface> getSortMap(Class<?> clazz) {
        return getAllFields(clazz).stream().filter(e -> e.getAnnotation(SortInterface.class) != null)
                .collect(Collectors.toMap(e -> e.getName(), e -> {
                    return (obj) -> {
                        if (e.getType().isEnum()) {
                            return EnumInterface.valueOf(obj.toString(), e.getType());
                        }
                        if (e.getType().isAssignableFrom(Long.class)) {
                            return Long.parseLong(obj.toString());
                        }
                        if (e.getType().isAssignableFrom(Integer.class)) {
                            return Integer.parseInt(obj.toString());
                        }
                        if (e.getType().isAssignableFrom(Boolean.class)) {
                            return Boolean.parseBoolean(obj.toString());
                        }
                        return obj.toString();
                    };
                }));
    }

}
