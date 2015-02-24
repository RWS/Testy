package com.sdl.selenium.web.utils;

import java.util.*;

public class OrderedProperties extends Properties {
    private Map<Object, Object> entries = new LinkedHashMap<Object, Object>();


    public String getProperty(String key) {
        return (String) entries.get(key);
    }

    public Enumeration keys() {
        return Collections.enumeration(entries.keySet());
    }

    public Enumeration elements() {
        return Collections.enumeration(entries.values());
    }

    public boolean contains(Object value) {
        return entries.containsValue(value);
    }

    public void putAll(Map<? extends Object, ? extends Object> map) {
        entries.putAll((Map<? extends String, ? extends String>) map);
    }

    public int size() {
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public boolean containsKey(Object key) {
        return entries.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return entries.containsValue(value);
    }

    public String get(Object key) {
        return (String)entries.get(key);
    }

    public Object put(Object key, Object value) {
        return entries.put( key, value);
    }

    public Object remove(Object key) {
        return entries.remove(key);
    }

    public void clear() {
        entries.clear();
    }

    public Set keySet() {
        return entries.keySet();
    }

    public Collection values() {
        return entries.values();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return entries.entrySet();
    }

    public boolean equals(Object o) {
        return entries.equals(o);
    }

    public int hashCode() {
        return entries.hashCode();
    }
}
