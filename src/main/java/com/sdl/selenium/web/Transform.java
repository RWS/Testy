package com.sdl.selenium.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Transform {

    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true);

    default <V> List<V> transformToObjectList(Class<V> type, List<List<String>> actualListOfList) throws JsonProcessingException {
        String json = mapper.writeValueAsString(type);
        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        String jsonPretty = jsonNode.toPrettyString();
        List<String> jsonList = new ArrayList<>(Arrays.asList(jsonPretty.split("\\n")));
        jsonList.removeIf(o -> o.startsWith("{") || o.endsWith("}"));
        List<V> actualList = actualListOfList.stream().map(i -> {
            V actualObject = null;
            try {
                List<String> result = new ArrayList<>();
                result.add("{");
                for (int j = 0; j < i.size(); j++) {
                    String replace = i.get(j).replace("\n", " ");
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
        return actualList;
    }
}