package com.sdl.selenium.web.utils;

import java.util.concurrent.Callable;

public record Call<V>(String name, Callable<V> callable) {
}
