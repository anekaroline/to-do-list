package br.com.project.todolist.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class EntityMerger {
    private EntityMerger() {
    }

    public static <T> void mergeData(T existingEntity, Map<String, Object> requestData) {
        Class<?> entityClass = existingEntity.getClass();

        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();

            if (requestData.containsKey(fieldName)) {
                try {
                    Method setter = entityClass.getMethod("set" + capitalize(fieldName), field.getType());

                    Object value = requestData.get(fieldName);

                    merge(existingEntity, field, value, setter);

                } catch (ReflectiveOperationException | ParseException e) {
                    throw new RuntimeException("Erro ao mesclar dados para o campo: " + fieldName, e);
                }
            }
        }
    }

    private static <T> void merge(T existingEntity, Field field, Object value, Method setter) throws ReflectiveOperationException, ParseException {

            if (value instanceof String value1 && (field.getType().equals(LocalDate.class))) {
                value = DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(value1, LocalDate::from);
            }
            setter.invoke(existingEntity, value);

    }

    private static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static <T> T criarInstancia(Class<T> clazz) throws ReflectiveOperationException {
        return clazz.getDeclaredConstructor().newInstance();
    }
}