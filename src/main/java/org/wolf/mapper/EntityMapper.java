package org.wolf.mapper;

import org.wolf.mapper.util.Entity;
import org.wolf.mapper.util.MetaModel;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

public class EntityMapper {

    private static final String START_LIST = "[";
    private static final String END_LIST = "]";
    private static final String START_OBJECT = "{";
    private static final String END_OBJECT = "}";

    public static <T> String writeValueAsString(T object) throws IllegalAccessException {
        if (object.getClass().isArray()) {
            return serializeEntityArray(object);
        } else if (List.class.isAssignableFrom(object.getClass())) {
            return serializeEntityArray(((List<?>) object).toArray());
        } else {
            return serializeEntity(object);
        }
    }

    private static <T> String serializeEntityArray(T object) throws IllegalAccessException {
        int size = Array.getLength(object);
        StringBuilder outputJson = new StringBuilder(START_LIST);
        for (int ind = 0; ind < size; ind++) {
            outputJson.append(serializeEntity(Array.get(object, ind)));
            if (ind != size - 1) {
                outputJson.append(",");
            }
        }
        outputJson.append(END_LIST);
        return outputJson.toString();
    }

    private static <T> String serializeEntity(T object) throws IllegalAccessException {
        MetaModel model = MetaModel.of(object.getClass());
        List<Entity> jsonEntities = model.getJsonEntities();
        StringBuilderWrapper outputJson = new StringBuilderWrapper(START_OBJECT);

        for (int index = 0; index < jsonEntities.size(); index++) {
            Entity entity = jsonEntities.get(index);
            Class<?> fieldType = entity.getType();
            String fieldName = entity.getName();
            Field field = entity.getField();
            field.setAccessible(true);
            if (field.get(object) == null) {
                if (index == jsonEntities.size() - 1) {
                    outputJson.deleteCharAt(outputJson.length() - 1);
                }
                continue;
            } else if (fieldType == String.class || fieldType == Character.class) {
                outputJson.appendKey(fieldName).appendSeparater().appendWrapped(field.get(object));
            } else if (fieldType == char.class) {
                outputJson.appendKey(fieldName).appendSeparater().appendWrapped(field.getChar(object));
            } else if (isBoxedNumber(fieldType)) {
                outputJson.appendKey(fieldName).appendSeparater().append(field.get(object));
            } else if (isIntegerPrimitive(fieldType)) {
                outputJson.appendKey(fieldName).appendSeparater().append(field.getLong(object));
            } else if (isFloatingPointPrimitive(fieldType)) {
                outputJson.appendKey(fieldName).appendSeparater().append(field.getDouble(object));
            } else {
                outputJson.appendKey(fieldName).appendSeparater().append(writeValueAsString(field.get(object)));
            }
            if (index != jsonEntities.size() - 1) {
                outputJson.append(",");
            }
        }
        outputJson.append(END_OBJECT);
        return outputJson.toString();
    }

    private static boolean isFloatingPointPrimitive(Class<?> fieldType) {
        return fieldType == float.class || fieldType == double.class;
    }

    private static boolean isIntegerPrimitive(Class<?> fieldType) {
        return fieldType == byte.class || fieldType == short.class || fieldType == int.class || fieldType == long.class;
    }

    private static boolean isBoxedNumber(Class<?> fieldType) {
        return fieldType.isAssignableFrom(Number.class);
    }

    public static <T> T readValue(String inputJson, Class<T> clss) {
        return null;
    }

    static class StringBuilderWrapper {
        StringBuilder outputJson;

        public StringBuilderWrapper(CharSequence sequence) {
            this.outputJson = new StringBuilder(sequence);
        }

        public StringBuilderWrapper(StringBuilder outputJson) {
            this.outputJson = outputJson;
        }

        public StringBuilder getOutputJson() {
            return outputJson;
        }

        public void setOutputJson(StringBuilder outputJson) {
            this.outputJson = outputJson;
        }

        public StringBuilderWrapper appendKey(String fieldName) {
            this.outputJson.append("\"").append(fieldName).append("\"");
            return this;
        }

        public StringBuilderWrapper appendSeparater() {
            this.outputJson.append(":");
            return this;
        }

        public StringBuilderWrapper append(Object o) {
            this.outputJson.append(o);
            return this;
        }

        public StringBuilderWrapper appendWrapped(Object o) {
            this.outputJson.append("\"").append(o).append("\"");
            return this;
        }

        @Override
        public String toString() {
            return outputJson.toString();
        }

        public int length() {
            return outputJson.length();
        }

        public void deleteCharAt(int i) {
            this.outputJson.deleteCharAt(i);
        }
    }
}
