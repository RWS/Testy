package com.sdl.selenium.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public interface Transform {

    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    default <V> List<V> transformTo(V type, List<List<String>> actualListOfList, List<Integer> columnsList) {
        String json = mapper.writeValueAsString(type);
        List<String> names = getNames(json);
        if (names.size() > actualListOfList.get(0).size()) {
            names.removeIf(i -> columnsList.contains(names.indexOf(i) + 1));
        }
        int size = names.size();
        LinkedList<V> resultList = new LinkedList<>();
        for (List<String> actualList : actualListOfList) {
            JsonNode jsonNode = mapper.readTree(json);
            for (int i = 0; i < size; i++) {
                String field = names.get(i);
                String value = i >= actualList.size() ? null : actualList.get(i);
                ((ObjectNode) jsonNode).put(field, value);
            }
            V object = mapper.treeToValue(jsonNode, (Class<V>) type.getClass());
            resultList.add(object);
        }
        return resultList;
    }

    private List<String> getNames(String json) throws JsonProcessingException {
        List<String> names = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(json);
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        List<String> list = new ArrayList<>();
        jsonNode.elements().next().fields().forEachRemaining(i -> {
            if (i.getValue().textValue() != null) {
                list.add(i.getKey());
            }
        });

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            String entry = next.getKey();
            String value = next.getValue().textValue();
            if (value != null) {
                names.add(entry);
            }
        }
        return names;
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

    @SneakyThrows
    default <V> V transformToObject(V type, List<String> actualList, List<Integer> columnsList) {
        String json = mapper.writeValueAsString(type);
        List<String> names = getNames(json);
        if (names.size() > actualList.size()) {
            names.removeIf(i -> columnsList.contains(names.indexOf(i) + 1));
        }
        int size = names.size();
        JsonNode jsonNode = mapper.readTree(json);
        for (int i = 0; i < size; i++) {
            String field = names.get(i);
            String value = i >= actualList.size() ? null : actualList.get(i);
            ((ObjectNode) jsonNode).put(field, value);
        }
        V object = mapper.treeToValue(jsonNode, (Class<V>) type.getClass());
        return object;
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