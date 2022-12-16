package com.sdl.selenium.web.utils;

public class Result<V> {
    V result;
    int position;
    boolean timeOut;

    public Result(V result, int position) {
        this.result = result;
        this.position = position;
    }

    public Result(V result, int position, boolean timeOut) {
        this(result, position);
        this.timeOut = timeOut;
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
