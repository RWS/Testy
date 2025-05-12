package com.sdl.selenium.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public interface Transform {

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Transforms a list of string lists into a list of objects of the specified type.
     * The method uses Jackson ObjectMapper for JSON serialization/deserialization to perform the transformation.
     *
     * @param type             the template object used for transformation
     * @param actualListOfList the list of data to be transformed
     * @param columnsList      list of column indices to be excluded from the transformation
     * @param <V>              the generic type of the resulting objects
     * @return a list of transformed objects
     */
    @SneakyThrows
    default <V> List<V> transformTo(V type, List<List<String>> actualListOfList, List<Integer> columnsList) {
        if (type == null) {
            return (List<V>) actualListOfList;
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(type);
        List<String> names = getNames(json);
        if (names.size() > actualListOfList.get(0).size()) {
            names.removeIf(i -> {
                int index = names.indexOf(i) + 1;
                return columnsList.contains(index);
            });
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
        jsonNode.fields().forEachRemaining(i -> {
            if (i.getValue().textValue() != null) {
                names.add(i.getKey());
            }
        });
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
            names.removeIf(i -> {
                int index = names.indexOf(i) + 1;
                return columnsList.contains(index);
            });
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

    default List<List<String>> equalizeLists(List<List<String>> values) {
        int maxLength = values.stream().mapToInt(List::size).max().orElse(0);
        List<List<String>> mutableValues = new ArrayList<>();
        for (List<String> list : values) {
            List<String> mutableList = new ArrayList<>(list);
            while (mutableList.size() < maxLength) {
                mutableList.add("");
            }
            mutableValues.add(mutableList);
        }
        return mutableValues;
    }
}