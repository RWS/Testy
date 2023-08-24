package com.sdl.selenium.web.utils;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response<V> {
    private V result;
    private boolean done;
}
