package com.cms.spring.jpa.postgresql.postgresql.DTO;

import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import lombok.Data;
import java.util.List;

@Data
public class CastDTO {
    private Long id;
    private String name;
    private String poster;
    //private List<Long> contentIds; // İlişkili content'lerin ID'lerini tutan bir alan

    public CastDTO(Cast castt) {
        this.id = castt.getId();
        this.name = castt.getName();
        this.poster = castt.getPoster();
    }


    public CastDTO() {
    }
}
