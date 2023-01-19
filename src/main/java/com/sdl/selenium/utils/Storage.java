package com.sdl.selenium.utils;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Storage {

//    @Autowired
    private Map<String, Object> cache = new HashMap<>();

    public Map<String, Object> getCache() {
        return cache;
    }

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
        if (matcher.find()) {
            String variableKey = matcher.group(1);
            name = get(variableKey);
            if (Strings.isNullOrEmpty(name) || name.contains(variableKey)) {
                String tmp = get(get(variableKey));
                name = tmp == null ? name : tmp;
                return name == null ? "Not found value for key: '" + variableKey + "'" : variable.replace(matcher.group(), name);
            }
            return variable.replace(matcher.group(), name);
        }
        return variable;
    }

    public String getKey(String value) {
        Optional<Map.Entry<String, Object>> find = cache.entrySet().stream().filter(entry -> !Objects.isNull(entry.getValue()) && Objects.equals(entry.getValue(), value)).findFirst();
        return find.map(Map.Entry::getKey).orElse(null);
    }
}
