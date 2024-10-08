package com.cms.spring.jpa.postgresql.postgresql.DTO;

import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentDTO {
    private MetadataDTO metadata;
    private Long id;
    private String director;
    private Timestamp createdAt;
    private Long metadataId; // Metadata ile olan ilişkiyi tutar
    private List<Long> actorIds; // İlişkili oyuncuların ID'lerini tutar
    private List<CastDTO> casts; // Cast DTO'larını tutar

    // DTO'ya entity'den dönüşüm yaparken her alanı set etmeliyiz
    public ContentDTO(Content content) {
        this.id = content.getId();
        this.director = content.getDirector();
        this.createdAt = content.getCreatedAt();
        this.metadataId = content.getMetadata().getId();
        this.actorIds = content.getCasts().stream().map(Cast::getId).collect(Collectors.toList());
    }

    public ContentDTO() { }

}


