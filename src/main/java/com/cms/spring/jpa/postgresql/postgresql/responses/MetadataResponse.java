package com.cms.spring.jpa.postgresql.postgresql.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MetadataResponse {

    private long id;
    private String title;
    private String plot;
    private int year;
    private String language;
    private String country;

}
