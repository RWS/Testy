package com.sdl.selenium.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.cucumber.java.Scenario;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AssertUtil {
    private final ObjectMapper mapper = new ObjectMapper();

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
        List<List<String>> logs = new ArrayList<>();
        for (Object list : values) {
            List<?> l = (List<E>) list;
            List<String> logTmp = new ArrayList<>();
            for (Object value : l) {
                if ("".equals(value)) {
                    logTmp.add("[blank]");
                } else {
                    logTmp.add(transformDate ? format.apply(value + "") : (String) value);
                }
            }
            logs.add(logTmp);
        }
        return formatLogs(logs);
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

    public <E, T extends List<E>> String showValuesVertical(T lists) {
        if (lists == null || lists.isEmpty()) {
            return null;
        }
        List<String> logs = new ArrayList<>();
        for (Object o : lists) {
            logs.add((String) o);
        }
        return formatLog(logs);
    }

    @SneakyThrows
    public <E, T extends List<E>> String showObjectValues(T lists, boolean transformDate, Function<String, String> function) {
        if (lists == null || lists.isEmpty()) {
            return null;
        }
        Scenario scenario = storage.get("currentScenario");
        String scenarioName = scenario.getName();
        Class aClass;
        try {
            aClass = lists.get(0).getClass();
            if (aClass.getName().contains("List")) {
                String currentValues = showValues(lists, transformDate, function);
                storage.set("currentValues-" + scenarioName, currentValues);
                return currentValues;
            } else if (lists.getClass().getName().contains("List") && lists.get(0).getClass().getName().contains("String")) {
                String currentValues = showValue(lists);
                storage.set("currentValues-" + scenarioName, currentValues);
                return currentValues;
            } else if (aClass.getDeclaredFields()[0].getType().getSimpleName().contains("Map")) {
                String currentValues = showMapValue(lists);
                storage.set("currentValues-" + scenarioName, currentValues);
                return currentValues;
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        List<String> names = getNames(mapper.writeValueAsString(lists.get(0)));
        Set<String> headers = new LinkedHashSet<>();
        List<List<String>> logs = new ArrayList<>();
        List<String> logTmp;
        for (Object o : lists) {
            logTmp = new ArrayList<>();
            E workFlow = (E) o;
            String json = mapper.writeValueAsString(workFlow);
            JsonNode node = mapper.readTree(json);
            for (String name : names) {
                String value = node.get(name).textValue();
                if (value != null) {
                    headers.add(name);
                    Object format;
                    if (value.isEmpty()) {
                        format = "[blank]";
                    } else {
                        format = transformDate ? function.apply(value) : value;
                    }
                    logTmp.add(format.toString());
                }
            }
            logs.add(logTmp);
        }
        logs.add(0, headers.stream().toList());
        String currentValues = formatLogs(logs);
        storage.set("currentValues-" + scenarioName, currentValues);
        return currentValues;
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

    private String formatLogs(List<List<String>> logs) {
        List<Integer> maxCharacterLength = findMaxCharacterLength(logs);
        List<List<String>> adjustsLogs = adjusts(logs, maxCharacterLength);
        StringBuilder log = new StringBuilder();
        log.append("\n");
        for (List<String> adjustsLog : adjustsLogs) {
            int size = maxCharacterLength.size() - adjustsLog.size();
            String joined = String.join(" | ", adjustsLog);
            String join = size > 0 ? joined + " | " + " ".repeat(maxCharacterLength.get(maxCharacterLength.size() - 1)) : joined;
            log.append("| ").append(join).append(" |\n");
        }
        log.append("\n");
        return log.toString();
    }

    private String formatLog(List<String> logs) {
        int maxLength = logs.stream().mapToInt(String::length).max().orElse(0);
        StringBuilder log = new StringBuilder("\n");
        for (String logEntry : logs) {
            int paddingLength = maxLength - logEntry.length();
            String paddedLogEntry = logEntry + " ".repeat(Math.max(0, paddingLength));
            log.append("| ").append(String.join(" | ", paddedLogEntry)).append(" |\n");
        }
        log.append("\n");
        return log.toString();
    }

    private List<Integer> findMaxCharacterLength(List<List<String>> logs) {
        return IntStream.range(0, logs.isEmpty() ? 0 : logs.get(0).size())
                .mapToObj(i -> logs.stream().mapToInt(row -> {
                    int count;
                    try {
                        count = row.get(i).length();
                    } catch (IndexOutOfBoundsException e) {
                        count = 0;
                    }
                    return count;
                }).max().orElse(0))
                .collect(Collectors.toList());
    }

    private List<List<String>> adjusts(List<List<String>> logs, List<Integer> columnsSize) {
        return logs.stream()
                .map(log -> IntStream.range(0, log.size())
                        .mapToObj(i -> {
                            String item = log.get(i);
                            int length = item.length();
                            int repeatInt = columnsSize.get(i) - length;
                            return item + " ".repeat(Math.max(0, repeatInt));
                        })
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
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

    public <O> O format(O dates) {
        if (dates instanceof String date) {
            if (!Strings.isNullOrEmpty(date)) {
                Pattern pattern = Pattern.compile("(\\d{2,4}[-. ]\\d{2,4}[-. ]\\d{2,4}(?:[ T]\\d{2}:\\d{2}:\\d{2})?|\\d{2} [A-Za-z]{3,9} \\d{4}(?:[ T]\\d{2}:\\d{2}:\\d{2})?|Today)");
                // Accept format 'dd MMM yyyy', 'yyyy MMM dd', 'dd MM yyyy', 'dd-MM-yyyy'
                Matcher matcher = pattern.matcher(date);
                if (matcher.find()) {
                    LocalDate now = LocalDate.now();
                    String group = matcher.group();
                    if (group.contains("Today")) {
                        return (O) "today dd MMM yyyy";
                    }
                    try {
                        Time timeObj = getLocalDate(group);
                        LocalDateTime newDateTime = timeObj.localDateTime();
                        String format = timeObj.format();
                        LocalDate newDate = (LocalDate) ChronoLocalDate.from(newDateTime);
                        Matcher m = Pattern.compile("\\s\\d{2}:\\d{2}:\\d{2}").matcher(group);
                        String time = m.find() ? m.group() : "";
                        if (now.minusDays(7L).isEqual(newDate)) {
                            return (O) ("1WeekAgo " + format + time);
                        } else if (now.minusDays(3L).isEqual(newDate)) {
                            return (O) ("3DaysAgo " + format + time);
                        } else if (now.minusDays(2L).isEqual(newDate)) {
                            return (O) ("2DaysAgo " + format + time);
                        } else if (now.minusDays(1L).isEqual(newDate)) {
                            return (O) ("yesterday " + format + time);
                        } else if (now.isEqual(newDate)) {
                            return (O) ("today " + format + time);
                        } else if (now.plusDays(1L).isEqual(newDate)) {
                            return (O) ("tomorrow " + format + time);
                        } else if (now.plusDays(2L).isEqual(newDate)) {
                            return (O) ("in2Days " + format + time);
                        } else if (now.plusDays(3L).isEqual(newDate)) {
                            return (O) ("in3Days " + format + time);
                        } else if (now.plusDays(4L).isEqual(newDate)) {
                            return (O) ("in4Days " + format + time);
                        } else if (now.plusDays(5L).isEqual(newDate)) {
                            return (O) ("in5Days " + format + time);
                        } else if (now.plusDays(6L).isEqual(newDate)) {
                            return (O) ("in6Days " + format + time);
                        } else if (now.plusDays(7L).isEqual(newDate)) {
                            return (O) ("nextWeek " + format + time);
                        } else if (now.plusDays(8L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd1Day " + format + time);
                        } else if (now.plusDays(9L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd2Days " + format + time);
                        } else if (now.plusDays(10L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd3Days " + format + time);
                        } else if (now.plusDays(11L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd4Days " + format + time);
                        } else if (now.plusDays(12L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd5Days " + format + time);
                        } else if (now.plusDays(13L).isEqual(newDate)) {
                            return (O) ("nextWeekAnd6Days " + format + time);
                        } else if (now.plusDays(14L).isEqual(newDate)) {
                            return (O) ("next2Weeks " + format + time);
                        } else if (now.plusDays(21L).isEqual(newDate)) {
                            return (O) ("next3Weeks " + format + time);
                        } else if (now.plusDays(28L).isEqual(newDate)) {
                            return (O) ("next4Weeks " + format + time);
                        } else if (now.plusMonths(1L).minusDays(1L).isEqual(newDate)) {
                            return (O) ("nextMonth1DayAgo " + format + time);
                        } else if (now.plusMonths(1L).minusDays(2L).isEqual(newDate)) {
                            return (O) ("nextMonth2DaysAgo " + format + time);
                        } else if (now.plusMonths(1L).minusDays(3L).isEqual(newDate)) {
                            return (O) ("nextMonth3DaysAgo " + format + time);
                        } else if (now.plusMonths(1L).plusDays(1L).isEqual(newDate)) {
                            return (O) ("nextMonthAnd1Day " + format + time);
                        } else if (now.plusMonths(1L).plusDays(2L).isEqual(newDate)) {
                            return (O) ("nextMonthAnd2Days " + format + time);
                        } else if (now.plusMonths(1L).isEqual(newDate)) {
                            return (O) ("nextMonth " + format + time);
                        } else if (now.plusMonths(6L).minusDays(1L).isEqual(newDate)) {
                            return (O) ("next6Months1DayAgo " + format + time);
                        } else if (now.plusMonths(6L).minusDays(2L).isEqual(newDate)) {
                            return (O) ("next6Months2DaysAgo " + format + time);
                        } else if (now.plusYears(1L).minusDays(1L).isEqual(newDate)) {
                            return (O) ("nextYear1DayAgo " + format + time);
                        } else if (now.plusYears(1L).isEqual(newDate)) {
                            return (O) ("nextYear " + format + time);
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

    record Format(DateTimeFormatter formatter, String format) {
    }

    record Time(LocalDateTime localDateTime, String format) {
    }

    public Time getLocalDate(String group) {
        List<Format> formatters = List.of(
                new Format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH), "dd MMM yyyy"),
                new Format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH), "yyyy-MM-dd"),
                new Format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH), "dd-MM-yyyy"),
                new Format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss", Locale.ENGLISH), "dd MMM yyyy"),
                new Format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH), "yyyy-MM-dd"),
                new Format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH), "dd-MM-yyyy")
        );
        String form = "";
        LocalDateTime newDate = null;
        for (Format format : formatters) {
            try {
                newDate = LocalDateTime.parse(group, format.formatter);
                form = format.format;
                break; // dacă a reușit, ieși din for
            } catch (DateTimeParseException ignored) {
            }
        }
        if (newDate == null) {
            throw new DateTimeParseException("No matching date format", group, 0);
        }
        return new Time(newDate, form);
    }

    public String getKeyFromStorage(String date) {
        String valueTmp;
        boolean isCopied = false;
        if (date.equals(".") || date.equals("...")) {
            return date;
        } else if (date.contains(".")) {
            valueTmp = date.split("\\.")[0];
            isCopied = true;
        } else {
            valueTmp = date;
        }
        if (storage != null) {
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
        } else {
            return date;
        }
    }
}