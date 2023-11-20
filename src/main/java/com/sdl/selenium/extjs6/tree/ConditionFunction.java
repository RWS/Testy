package com.sdl.selenium.extjs6.tree;

import lombok.Getter;

import java.util.function.Function;
import java.util.function.Predicate;

@Getter
public class ConditionFunction<T, P, R> {
    private final Predicate<P> condition;
    private final Function<T, R> function;

    public ConditionFunction(Predicate<P> condition, Function<T, R> function) {
        this.condition = condition;
        this.function = function;
    }
}
