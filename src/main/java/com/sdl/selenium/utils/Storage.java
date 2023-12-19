package com.sdl.selenium.utils;

import com.google.common.base.Strings;
import com.sdl.selenium.web.utils.Utils;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Component
public class Storage {

    private final Map<String, Object> cache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) cache.get(key);
    }

    public void set(String key, Object value) {
        cache.put(key, value);
    }

    public String replaceVariable(String variable) {
        String name;
        Pattern pattern = Pattern.compile("\\{(\\S+)}");
        Matcher matcher = pattern.matcher(variable);
        while (matcher.find()) {
            String variableKey = matcher.group(1);
            name = get(variableKey);
            String group = matcher.group();
            if (Strings.isNullOrEmpty(name) || name.contains(variableKey)) {
                String tmp = get(get(variableKey));
                name = tmp == null ? name : tmp;
                variable = name == null ? "Not found value for key: '" + variableKey + "'" : variable.replace(group, name);
            } else {
                variable = variable.replace(group, name);
            }
        }
        return variable;
    }

    public String replaceVariable(String variable, String regex) {
        String name;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(variable);
        while (matcher.find()) {
            String variableKey = matcher.group(1);
            name = get(variableKey);
            String group = matcher.group();
            if (Strings.isNullOrEmpty(name) || name.contains(variableKey)) {
                String tmp = get(get(variableKey));
                name = tmp == null ? name : tmp;
                variable = name == null ? "Not found value for key: '" + variableKey + "'" : variable.replace(group, name);
            } else {
                variable = variable.replace(group, name);
            }
        }
        return variable;
    }

    public static void main(String[] args) {
        Storage storage = new Storage();
        storage.set("projectName", "p0");
        storage.set("projectName1", "p1");
        storage.set("projectName2", "p2");
        String var = "The file replacement for the projects {projectName}, {projectName1}, {projectName2} was saved for later. Click here to return to the Replace Files screen.";
        String s = storage.replaceVariable(var);
        Utils.sleep(1);
    }

    public String getKey(String value) {
        Optional<Map.Entry<String, Object>> find = cache.entrySet().stream().filter(entry -> !Objects.isNull(entry.getValue()) && Objects.equals(entry.getValue(), value)).findFirst();
        return find.map(Map.Entry::getKey).orElse(null);
    }
}
