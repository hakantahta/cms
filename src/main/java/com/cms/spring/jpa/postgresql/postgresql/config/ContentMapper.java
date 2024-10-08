package com.cms.spring.jpa.postgresql.postgresql.config;

import com.cms.spring.jpa.postgresql.postgresql.DTO.ContentDTO;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Mapper(componentModel = "spring")
public interface ContentMapper {

    // Timestamp dönüşümü
    default Timestamp map(String value) {
        return value != null ? Timestamp.valueOf(value) : null;
    }

    // Content'ten ContentDTO'ya dönüşüm
    ContentDTO toDTO(Content content);

    // List<Content> -> List<ContentDTO>
    default List<ContentDTO> toDTOs(List<Content> contents) {
        return contents.stream()
                .map(this::toDTO) // Her Content nesnesini ContentDTO'ya dönüştür
                .collect(Collectors.toList()); // Dönüştürülmüş nesneleri listeye ekle
    }

    // ContentDTO'dan Content entity'sine dönüşüm
    Content toEntity(ContentDTO contentDTO);
}
