package com.sdl.selenium.extjs6.grid;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
public class Options<V> {
    private V type;
    private boolean expand;
    private Predicate<Integer> predicate;
    private Function<Cell, String> function;
    private Function<Details<V>, List<List<String>>> collector;
    private Map<Predicate<Integer>, Function<Cell, String>> functions = new HashMap<>();

    public Options(V type) {
        this.type = type;
    }

    public Options(V type, Predicate<Integer> predicate, Function<Cell, String> function) {
        this.type = type;
        this.functions = Map.of(predicate, function);
    }

    public Options(V type, boolean expand, Predicate<Integer> predicate, Function<Cell, String> function) {
        this(type, predicate, function);
        this.expand = expand;
    }

    public Options(V type, boolean expand, Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(type, expand, predicate, function);
        this.collector = collector;
    }

    public Options(V type, Function<Details<V>, List<List<String>>> collector) {
        this(type);
        this.collector = collector;
    }

    public Options(Predicate<Integer> predicate, Function<Cell, String> function) {
        this.functions = Map.of(predicate, function);
    }

    public Options(Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(predicate, function);
        this.collector = collector;
    }

    public Options(boolean expand, Predicate<Integer> predicate, Function<Cell, String> function) {
        this(predicate, function);
        this.expand = expand;
    }

    public Options(boolean expand, Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(expand, predicate, function);
        this.collector = collector;
    }
}
