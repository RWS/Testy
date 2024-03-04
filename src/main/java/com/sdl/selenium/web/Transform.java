package com.sdl.selenium.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sdl.selenium.web.utils.Utils;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public interface Transform {

    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    default <V> List<V> transformTo(V type, List<List<String>> actualListOfList) {
        String json = mapper.writeValueAsString(type);
        List<String> names = getNames(json);
        int size = names.size();
        LinkedList<V> resultList = new LinkedList<>();
        for (List<String> actualList : actualListOfList) {
            JsonNode jsonNode = mapper.readTree(json);
            for (int i = 0; i < size; i++) {
                String value = i >= actualList.size() ? null : actualList.get(i);
                ((ObjectNode) jsonNode).put(names.get(i), value);
                Utils.sleep(1);
            }
            V object = mapper.treeToValue(jsonNode, (Class<V>) type.getClass());
            resultList.add(object);
        }
        return resultList;
    }

    private List<String> getNames(String json) throws JsonProcessingException {
        List<String> names = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(json);
        Iterator<String> fields = jsonNode.fieldNames();
        while (fields.hasNext()) {
            String entry = fields.next();
            names.add(entry);
        }
        return names;
    }

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