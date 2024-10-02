package com.cms.spring.jpa.postgresql.postgresql.SimpleResponse;

import lombok.Getter;
import lombok.Setter;

public class SimpleContentResponse {
    @Getter
    @Setter
    private long id;
    private String director;

    // Constructor
    public SimpleContentResponse(long id, String director) {
        this.id = id;
        this.director = director;
    }
}
