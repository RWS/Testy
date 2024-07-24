package com.sdl.selenium.extjs6.grid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Details<V> {
    private Options<V> options;
    private List<Integer> columns;
    private Grid grid;
    private Integer index;
}
