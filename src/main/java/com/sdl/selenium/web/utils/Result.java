package com.sdl.selenium.web.utils;

public class Result<V> {
    V result;
    int position;

    public Result(V result, int position) {
        this.result = result;
        this.position = position;
    }

    public V result() {
        return result;
    }

    public int position() {
        return position;
    }
}
