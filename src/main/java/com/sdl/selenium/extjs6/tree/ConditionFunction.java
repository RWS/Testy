package com.sdl.selenium.extjs6.tree;

import java.util.function.Function;
import java.util.function.Predicate;

public class ConditionFunction<T, R> {
    private final Predicate<T> condition;
    private final Function<T, R> function;

    public ConditionFunction(Predicate<T> condition, Function<T, R> function) {
        this.condition = condition;
        this.function = function;
    }

    public Predicate<T> getCondition() {
        return condition;
    }

    public Function<T, R> getFunction() {
        return function;
    }
}
