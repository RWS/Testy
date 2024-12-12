package com.sdl.selenium.web.utils;

import lombok.ToString;

@ToString
public class Result<V> {
    V result;
    int position;
    boolean timeOut;
    String field;

    public Result(V result, int position) {
        this.result = result;
        this.position = position;
    }

    public Result(V result, int position, boolean timeOut) {
        this(result, position);
        this.timeOut = timeOut;
    }

    public Result(V result, int position, boolean timeOut, String field) {
        this(result, position, timeOut);
        this.field = field;
    }

    public V result() {
        return result;
    }

    public int position() {
        return position;
    }

    public boolean timeOut() {
        return timeOut;
    }
}
