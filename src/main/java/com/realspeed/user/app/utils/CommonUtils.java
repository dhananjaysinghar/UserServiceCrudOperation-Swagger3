package com.realspeed.user.app.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
public class CommonUtils {

    @SneakyThrows
    public static <T, R> void copyValues(@NotNull T source, @NotNull R dest) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);
            if (Optional.ofNullable(value).isPresent()) {
                String fieldName = field.getName();
                try {
                    Field consField = dest.getClass().getDeclaredField(fieldName);
                    if (consField != null) {
                        consField.setAccessible(true);
                        consField.set(dest, value);
                    }
                } catch (Exception ex) {
                    log.debug("op=copy_data status=ERROR, desc=error occurred during data copy {}", ex.getMessage());
                }
            }
        }
    }
}
