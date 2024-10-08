package com.cms.spring.jpa.postgresql.postgresql.DTO;

import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import lombok.Data;


@Data
public class MetadataDTO {
    private Long id;
    private String title;
    private String plot;
    private int year;
    private String language;
    private String country;

    public MetadataDTO(Metadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Metadata cannot be null");
        }
        this.id = metadata.getId();
        this.title = metadata.getTitle();
        this.plot = metadata.getPlot();
        this.year = metadata.getYear();
        this.language = metadata.getLanguage();
        this.country = metadata.getCountry();
    }

    public MetadataDTO() {
        // Varsayılan yapıcı
    }
}
