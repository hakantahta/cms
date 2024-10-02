package com.cms.spring.jpa.postgresql.postgresql.SimpleResponse;


import lombok.Data;

@Data
public class SimpleMetadataResponse {
    private long id;
    private String name;
    private String title;

    // Yapıcı metot
    public SimpleMetadataResponse(Long id, String name) {
        this.id = id;
        this.name = name;
        this.title = title;
    }
    public SimpleMetadataResponse() {

    }
}
