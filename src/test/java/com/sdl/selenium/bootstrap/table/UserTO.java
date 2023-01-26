package com.sdl.selenium.bootstrap.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTO {
    private String first;
    private String last;
    private String email;

    public UserTO(String first, String last, String email) {
        this.first = first;
        this.last = last;
        this.email = email;
    }
}
