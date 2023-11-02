package com.sdl.selenium.bootstrap.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
