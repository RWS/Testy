package com.sdl.selenium.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.selenium.web.utils.Utils;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public interface Transform {

    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true);

    /**
     * add in V class this: @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    @SneakyThrows
    default <V> List<V> transformToObjectList(V type, List<List<String>> actualListOfList) {
        String json = mapper.writeValueAsString(type);
        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        String jsonPretty = jsonNode.toPrettyString();
        List<String> jsonList = new ArrayList<>(Arrays.asList(jsonPretty.split("\\n")));
        jsonList.removeIf(o -> o.startsWith("{") || o.endsWith("}"));
        List<V> collect = actualListOfList.stream().map(i -> {
            V actualObject = null;
            try {
                List<String> result = new ArrayList<>();
                result.add("{");
                for (int j = 0; j < i.size(); j++) {
                    String replace;
                    try {
                        replace = i.get(j).replace("\n", " ");
                    } catch (NullPointerException e) {
                        Utils.sleep(1);
                        replace = i.get(j).replace("\n", " ");
                    }
                    String oldValue = jsonList.get(j);
                    if (oldValue.contains(":")) {
                        String tmp = oldValue.replaceAll(":([^{}]*)\"", ":\"" + replace + "\"");
                        result.add(tmp);
                    } else {
                        result.add(oldValue);
                    }
                }
                result.add("}");
                String jsonActual = String.join("", result);
                actualObject = (V) mapper.readValue(jsonActual, type.getClass());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return actualObject;
        }).collect(Collectors.toList());
        return new LinkedList<>(collect);
    }

    default <V> List<V> transformToObjectList(Class<V> type, List<List<String>> actualListOfList) {
        Class<?> newClazz;
        int size = actualListOfList.get(0).size();
        Constructor<?> constructor = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            Constructor<?>[] constructors = newClazz.getConstructors();
            for (Constructor<?> c : constructors) {
                int parameterCount = c.getParameterCount();
                if (size == parameterCount) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final Constructor<V> finalConstructor = (Constructor<V>) constructor;
        return actualListOfList.stream().map(t -> {
            try {
                return finalConstructor.newInstance(t.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    /**
     * add in V class this: @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    @SneakyThrows
    default <V> V transformToObject(V type, List<String> actualList) {
        String json = mapper.writeValueAsString(type);
        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        String jsonPretty = jsonNode.toPrettyString();
        List<String> jsonList = new ArrayList<>(Arrays.asList(jsonPretty.split("\\n")));
        jsonList.removeIf(o -> o.startsWith("{") || o.endsWith("}"));
        V actualObject = null;
        try {
            List<String> result = new ArrayList<>();
            result.add("{");
            for (int j = 0; j < actualList.size(); j++) {
                String replace = actualList.get(j).replace("\n", " ");
                String oldValue = jsonList.get(j);
                if (oldValue.contains(":")) {
                    String tmp = oldValue.replaceAll(":([^{}]*)\"", ":\"" + replace + "\"");
                    result.add(tmp);
                } else {
                    result.add(oldValue);
                }
            }
            result.add("}");
            String jsonActual = String.join("", result);
            actualObject = (V) mapper.readValue(jsonActual, type.getClass());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return actualObject;
    }

    default <V> V transformToObject(Class<V> type, List<String> cellsText) {
        int fieldsCount;
        Constructor constructor = null;
        try {
            Class<?> newClazz = Class.forName(type.getTypeName());
            fieldsCount = cellsText.size();
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                if (fieldsCount == c.getParameterCount()) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Constructor<V> constructorTemp = (Constructor<V>) constructor;
            return constructorTemp.newInstance(cellsText.toArray(new Object[0]));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}