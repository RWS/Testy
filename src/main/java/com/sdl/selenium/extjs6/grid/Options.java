package com.sdl.selenium.extjs6.grid;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Configuration class for ExtJS grid operations.
 * This class provides flexible options for handling grid data processing and cell manipulation.
 *
 * @param <V> The type parameter for the grid data
 */
@Getter
@Setter
@ToString
public class Options<V> {
    /**
     * The type of data being processed
     */
    private V type;

    /**
     * Flag indicating if the grid should be expanded
     */
    private boolean expand;

    /**
     * Predicate for filtering rows
     */
    private Predicate<Integer> predicate;

    /**
     * Function for processing cell data
     */
    private Function<Cell, String> function;

    /**
     * Collector function for gathering grid details
     */
    private Function<Details<V>, List<List<String>>> collector;

    /**
     * Map of predicates to their corresponding cell processing functions
     */
    private Map<Predicate<Integer>, Function<Cell, String>> functions = new HashMap<>();

    /**
     * Creates a new Options instance with the specified type.
     *
     * @param type The type of data to be processed
     */
    public Options(V type) {
        this.type = type;
    }

    /**
     * Creates a new Options instance with type, predicate, and function.
     *
     * @param type      The type of data to be processed
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     */
    public Options(V type, Predicate<Integer> predicate, Function<Cell, String> function) {
        this.type = type;
        this.functions = Map.of(predicate, function);
    }

    /**
     * Creates a new Options instance with type and expansion flag.
     *
     * @param type   The type of data to be processed
     * @param expand Whether the grid should be expanded
     */
    public Options(V type, boolean expand) {
        this(type);
        this.expand = expand;
    }

    /**
     * Creates a new Options instance with type, expansion flag, predicate, and function.
     *
     * @param type      The type of data to be processed
     * @param expand    Whether the grid should be expanded
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     */
    public Options(V type, boolean expand, Predicate<Integer> predicate, Function<Cell, String> function) {
        this(type, predicate, function);
        this.expand = expand;
    }

    /**
     * Creates a new Options instance with type, expansion flag, predicate, function, and collector.
     *
     * @param type      The type of data to be processed
     * @param expand    Whether the grid should be expanded
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     * @param collector The collector function for gathering grid details
     */
    public Options(V type, boolean expand, Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(type, expand, predicate, function);
        this.collector = collector;
    }

    /**
     * Creates a new Options instance with type and collector.
     *
     * @param type      The type of data to be processed
     * @param collector The collector function for gathering grid details
     */
    public Options(V type, Function<Details<V>, List<List<String>>> collector) {
        this(type);
        this.collector = collector;
    }

    /**
     * Creates a new Options instance with type and functions map.
     *
     * @param type      The type of data to be processed
     * @param functions Map of predicates to their corresponding cell processing functions
     */
    public Options(V type, Map<Predicate<Integer>, Function<Cell, String>> functions) {
        this(type);
        this.functions = functions;
    }

    /**
     * Creates a new Options instance with type and functions map.
     *
     * @param type      The type of data to be processed
     * @param expand    Whether the grid should be expanded
     * @param functions Map of predicates to their corresponding cell processing functions
     */
    public Options(V type, boolean expand, Map<Predicate<Integer>, Function<Cell, String>> functions) {
        this(type, expand);
        this.functions = functions;
    }

    /**
     * Creates a new Options instance with predicate and function.
     *
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     */
    public Options(Predicate<Integer> predicate, Function<Cell, String> function) {
        this.functions = Map.of(predicate, function);
    }

    /**
     * Creates a new Options instance with predicate, function, and collector.
     *
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     * @param collector The collector function for gathering grid details
     */
    public Options(Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(predicate, function);
        this.collector = collector;
    }

    /**
     * Creates a new Options instance with expander flag.
     *
     * @param expand Whether the grid has row expander
     */
    public Options(boolean expand) {
        this.expand = expand;
    }

    /**
     * Creates a new Options instance with expander flag, predicate, and function.
     *
     * @param expand    Whether the grid has row expander
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     */
    public Options(boolean expand, Predicate<Integer> predicate, Function<Cell, String> function) {
        this(predicate, function);
        this.expand = expand;
    }

    /**
     * Creates a new Options instance with expander flag, predicate, function, and collector.
     *
     * @param expand    Whether the grid has row expander
     * @param predicate The predicate for filtering rows
     * @param function  The function for processing cell data
     * @param collector The collector function for gathering grid details
     */
    public Options(boolean expand, Predicate<Integer> predicate, Function<Cell, String> function, Function<Details<V>, List<List<String>>> collector) {
        this(expand, predicate, function);
        this.collector = collector;
    }
}
