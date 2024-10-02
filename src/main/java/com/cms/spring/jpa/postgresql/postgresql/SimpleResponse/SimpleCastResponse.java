package com.cms.spring.jpa.postgresql.postgresql.SimpleResponse;


import lombok.Data;

@Data
public class SimpleCastResponse {
    private long id;
    private String name;
    //private String poster;  poster alanını kullanacaksak bunu ekleyebiliriz. parametreli constructor(yapıcı metod) a eklemeyi ununtma.

    public SimpleCastResponse(Long id, String name) {
        this.id = id;
        this.name = name;

    }
    public SimpleCastResponse() {

    }

}
