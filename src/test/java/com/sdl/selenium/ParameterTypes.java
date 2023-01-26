package com.sdl.selenium;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.selenium.utils.Storage;
import io.cucumber.java.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterTypes {

    private static Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        ParameterTypes.storage = storage;
    }
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

    @ParameterType(name = "list", value = "\"[\\d_,;:.\\[\\]{}+=~#$%`\\\\&\\-\\pL\\pM'!()<>\\s™®©?@^\\p{Sc}\\/؟]+\"")
    public List<String> list(String strings) {
        String s = strings.replaceAll("^\"|\"$", "");
        String first = s.substring(0, 1);
        String splitChar = ",";
        if (first.equals(";")) {
            splitChar = ";";
            s = s.substring(1);
        }
        if (s.equals("[blank]")) {
            s = "";
        }
        return Arrays.stream(s.split(splitChar)).map(i -> variable(i).trim()).collect(Collectors.toList());
    }

    @ParameterType(name = "variable", value = "(.*)")
    public String variable(String variable) {
        variable = variable.replaceAll("^\"|\"$", "");
        return storage.replaceVariable(variable);
    }
}
