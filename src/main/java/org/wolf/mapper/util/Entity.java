package org.wolf.mapper.util;

import java.lang.reflect.Field;

public class Entity {
    private Field field;

    public Entity(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getName() {
        return field.getName();
    }
}
