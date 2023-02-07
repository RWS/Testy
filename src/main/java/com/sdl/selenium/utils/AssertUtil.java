package com.sdl.selenium.utils;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

@Component
public class AssertUtil {

    private static Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        if (AssertUtil.storage == null) {
            AssertUtil.storage = storage;
        }
    }

    private <E, T extends List<E>> String showValues(T values, boolean transformDate, Function<String, String> format) {
        if (values == null) {
            return null;
        }
        String s = logValues(values, transformDate, format);
        return s;
    }

    private <E, T extends List<E>> String logValues(T values, boolean transformDate, Function<String, String> format) {
        StringBuilder log = new StringBuilder();
        log.append("\n");
        for (Object list : values) {
            List<?> l = (List<E>) list;
            log.append(" | ");
            for (Object value : l) {
                if ("".equals(value)) {
                    log.append("[blank]").append(" | ");
                } else if (value == null) {
                    log.append(" | ");
                } else {
                    log.append(transformDate ? format.apply(String.valueOf(value)) : value).append(" | ");
                }
            }
            log.append("\n");
        }
        String s = log.toString();
        return s;
    }

    private static <E, T extends List<E>> String showValue(T values) {
        if (values == null) {
            return null;
        }
        return String.join(", ", (List) values);
    }

    public <T> String showObjects(T object, boolean transformDate) {
        if (object == null) {
            return null;
        }
        Class aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        StringBuilder log = new StringBuilder();
        List<String> fieldsList = new ArrayList<>();
        log.append("\n| ");
        for (Field f : fields) {
            String name = f.getName();
            log.append(name).append(" | ");
            String finalName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String typeName = f.getType().getSimpleName();
            if ("String".equals(typeName) || "List".equals(typeName)) {
                fieldsList.add("get" + finalName);
            } else if ("boolean".equals(typeName)) {
                fieldsList.add("is" + finalName);
            }
        }
        log.append("\n");
        log.append(" | ");
        for (String name : fieldsList) {
            try {
                Method method = object.getClass().getDeclaredMethod(name);
                Object value = method.invoke(object);
                if (value instanceof List) {
                    log.append(value.toString().replace("[", "").replace("]", "")).append(" | ");
                } else {
                    Object format = transformDate ? format(value) : value;
                    log.append(format).append(" | ");
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        log.append("\n");
        return log.toString();
    }

    public <E, T extends List<E>> String showObjectValues(T lists, boolean transformDate, Function<String, String> function) {
        if (lists == null || lists.isEmpty()) {
            return null;
        }
        Class aClass;
        try {
            aClass = lists.get(0).getClass();
            if (aClass.getName().contains("List")) {
                return showValues(lists, transformDate, function);
            } else if (lists.getClass().getName().contains("List") && lists.get(0).getClass().getName().contains("String")) {
                return showValue(lists);
            } else if (aClass.getDeclaredFields()[0].getType().getSimpleName().contains("Map")) {
                return showMapValue(lists);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        Field[] fields = aClass.getDeclaredFields();
        StringBuilder log = new StringBuilder();
        List<String> fieldsList = new ArrayList<>();
        log.append("\n| ");
        for (Field f : fields) {
            String name = f.getName();
            log.append(name).append(" | ");
            String finalName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String typeName = f.getType().getSimpleName();
            if ("String".equals(typeName) || "Boolean".equals(typeName)) {
                fieldsList.add("get" + finalName);
            } else if ("boolean".equals(typeName)) {
                fieldsList.add("is" + finalName);
            }
        }
        log.append("\n");
        for (Object o : lists) {
            log.append(" | ");
            E workFlow = (E) o;
            for (String name : fieldsList) {
                try {
                    Method method = workFlow.getClass().getDeclaredMethod(name);
                    String value = (String) method.invoke(workFlow);
                    Object format;
                    if ("".equals(value)) {
                        format = "[blank]";
                    } else if (value == null) {
                        format = "";
                    } else {
                        format = transformDate ? function.apply(value) : value;
                    }
                    log.append(format).append(" | ");
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            log.append("\n");
        }
        return log.toString();
    }


    private <E, T extends List<E>> String showMapValue(T lists) {
        StringBuilder log = new StringBuilder();
        Class aClass = lists.get(0).getClass();
        Field[] fields = aClass.getDeclaredFields();
        List<String> fieldsList = new ArrayList<>();
        for (Field f : fields) {
            String name = f.getName();
            String finalName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String typeName = f.getType().getSimpleName();
            if (typeName.contains("Map")) {
                fieldsList.add("get" + finalName);
            }
        }
        LinkedHashSet<String> keys = new LinkedHashSet<>();
        for (Object o : lists) {
            try {
                Method getKeysMethod = aClass.getMethod("getMap");
                keys.addAll(((LinkedHashMap<String, String>) getKeysMethod.invoke(o)).keySet());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        log.append("\n| ");
        for (String key : keys) {
            log.append(key).append(" | ");
        }
        log.append("\n");

        String name = fieldsList.get(0);
        Map<String, List<String>> maps = new HashMap<>();
        for (String key : keys) {
            for (E o : lists) {
                try {
                    Method method = o.getClass().getDeclaredMethod(name, String.class);
                    Object value = method.invoke(o, key);
                    if (value != null) {
                        maps.put(key, (List<String>) value);
                        break;
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        int maxSize = 0;
        for (String key : keys) {
            List<String> list = maps.get(key);
            maxSize = Math.max(list.size(), maxSize);
        }

        for (int i = 0; i < maxSize; i++) {
            log.append(" | ");
            for (String key : keys) {
                List<String> values = maps.get(key);
                String val = "";
                try {
                    val = values.get(i);
                } catch (IndexOutOfBoundsException e) {
                    // nothing to do
                }
                log.append(val).append(" | ");
            }
            log.append("\n");
        }
        return log.toString();
    }

    public Function<String, String> getFormat() {
        return this::format;
    }

    private <O> O format(O dates) {
        if (dates instanceof String) {
            String date = (String) dates;
            if (!Strings.isNullOrEmpty(date)) {
                Pattern pattern = Pattern.compile("\\d{2,4}[-\\sa-zA-Z0-9]{4,6}\\d{2,4}|(Today)");
                // Accept format 'dd MMM yyyy', 'yyyy MMM dd', 'dd MM yyyy', 'dd-MM-yyyy'
                java.util.regex.Matcher matcher = pattern.matcher(date);
                if (matcher.find()) {
                    LocalDate now = LocalDate.now();
                    String group = matcher.group();
                    if (group.contains("Today")) {
                        return (O) "today dd MMM yyyy";
                    }
                    try {
                        LocalDate newDate = LocalDate.parse(group, DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
                        if (now.isEqual(newDate)) {
                            return (O) "today dd MMM yyyy";
                        } else if (now.minusDays(1L).isEqual(newDate)) {
                            return (O) "yesterday dd MMM yyyy";
                        } else if (now.minusDays(2L).isEqual(newDate)) {
                            return (O) "2DaysAgo dd MMM yyyy";
                        } else if (now.minusDays(3L).isEqual(newDate)) {
                            return (O) "3DaysAgo dd MMM yyyy";
                        } else if (now.plusDays(1L).isEqual(newDate)) {
                            return (O) "tomorrow dd MMM yyyy";
                        } else if (now.plusDays(2L).isEqual(newDate)) {
                            return (O) "in2Days dd MMM yyyy";
                        } else if (now.plusDays(3L).isEqual(newDate)) {
                            return (O) "in3Days dd MMM yyyy";
                        } else if (now.plusDays(7L).isEqual(newDate)) {
                            return (O) "nextWeek dd MMM yyyy";
                        } else if (now.plusMonths(1L).minusDays(1L).isEqual(newDate)) {
                            return (O) "nextMonth1DayAgo dd MMM yyyy";
                        } else if (now.plusMonths(1L).minusDays(2L).isEqual(newDate)) {
                            return (O) "nextMonth2DaysAgo dd MMM yyyy";
                        } else if (now.plusMonths(1L).minusDays(3L).isEqual(newDate)) {
                            return (O) "nextMonth3DaysAgo dd MMM yyyy";
                        } else if (now.plusMonths(1L).plusDays(1L).isEqual(newDate)) {
                            return (O) "nextMonthAnd1Day dd MMM yyyy";
                        } else if (now.plusMonths(1L).plusDays(2L).isEqual(newDate)) {
                            return (O) "nextMonthAnd2Days dd MMM yyyy";
                        } else if (now.plusMonths(1L).isEqual(newDate)) {
                            return (O) "nextMonth dd MMM yyyy";
                        } else if (now.plusMonths(6L).minusDays(1L).isEqual(newDate)) {
                            return (O) "next6Months1DayAgo dd MMM yyyy";
                        } else if (now.plusMonths(6L).minusDays(2L).isEqual(newDate)) {
                            return (O) "next6Months2DaysAgo dd MMM yyyy";
                        } else if (now.plusYears(1L).minusDays(1L).isEqual(newDate)) {
                            return (O) "nextYear1DayAgo dd MMM yyyy";
                        } else if (now.plusYears(1L).isEqual(newDate)) {
                            return (O) "nextYear dd MMM yyyy";
                        }
                    } catch (DateTimeParseException e) {
                        String format = getKeyFromStorage(date);
                        return (O) format;
                    }
                } else {
                    String format = getKeyFromStorage(date);
                    return (O) format;
                }
            }
        }
        return dates;
    }

    public String getKeyFromStorage(String date) {
        String valueTmp;
        boolean isCopied = false;
        if (date.contains(".")) {
            valueTmp = date.split("\\.")[0];
            isCopied = true;
        } else {
            valueTmp = date;
        }
        String key = storage.getKey(valueTmp);
        if (Strings.isNullOrEmpty(key)) {
            key = storage.getKey(storage.getKey(valueTmp));
        }
        String value;
        if (key == null) {
            value = date;
        } else {
            if (date.contains(".")) {
                if (isCopied) {
                    value = date.replace(valueTmp, "{" + key + "}");
                } else {
                    value = date.replace(key, "{" + key + "}");
                }
            } else {
                value = "{" + key + "}";
            }
        }
        return value;
    }
}