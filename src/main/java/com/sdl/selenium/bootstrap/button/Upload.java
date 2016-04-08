package com.sdl.selenium.bootstrap.button;

public interface Upload {

    @Deprecated
    boolean upload(String... filePath);

    boolean upload(String filePath);
}
