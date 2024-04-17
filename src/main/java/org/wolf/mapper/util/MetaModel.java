package org.wolf.mapper.util;

import org.wolf.mapper.annotation.JsonEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetaModel {
    private Class<?> clss;

    public MetaModel(Class<?> clss) {
        this.clss = clss;
    }

    public static <U> MetaModel of(Class<U> clss) {
        return new MetaModel(clss);
    }

    public List<Entity> getJsonEntities() {
        Field[] fields = clss.getDeclaredFields();
        List<Entity> entities = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonEntity.class)) {
                entities.add(new Entity(field));
            }
        }
        return entities;
    }
}
