package com.sdl.selenium;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.*;

import java.lang.reflect.Type;

public class ParameterTypes {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer(replaceWithEmptyString = "[blank]")
    @DefaultDataTableCellTransformer(replaceWithEmptyString = "[blank]")
    public Object transformer(Object fromValue, Type toValueType) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String listOfStringListsType(String cell) {
        return cell;
    }

    @ParameterType(value = "(true|false)")
    public Boolean bool(String string) {
        return (Boolean) transformer(string, Boolean.class);
    }

    @ParameterType(value = "(is present|is not present)")
    public Boolean present(String string) {
        if ("is present".equals(string)) {
            return true;
        } else if ("is not present".equals(string)) {
            return false;
        } else {
            return null;
        }
    }

    @ParameterType(value = "(enabled|disabled)")
    public Boolean status(String string) {
        if ("enabled".equals(string)) {
            return true;
        } else if ("disabled".equals(string)) {
            return false;
        } else {
            return null;
        }
    }

    @ParameterType(value = "(check|uncheck)")
    public Boolean check(String string) {
        if ("check".equals(string)) {
            return true;
        } else if ("uncheck".equals(string)) {
            return false;
        } else {
            return null;
        }
    }
}
