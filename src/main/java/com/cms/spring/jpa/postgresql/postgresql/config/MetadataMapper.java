package com.cms.spring.jpa.postgresql.postgresql.config;

import com.cms.spring.jpa.postgresql.postgresql.DTO.MetadataDTO;
import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MetadataMapper {
    MetadataMapper INSTANCE = Mappers.getMapper(MetadataMapper.class);


    public default MetadataDTO toMetadataDTO(Metadata metadata) {
        if (metadata == null) {
            System.out.println("Received null metadata in toMetadataDTO method.");
            throw new IllegalArgumentException("Metadata cannot be null");
        }
        return new MetadataDTO(metadata);
    }

    // List<Metadata> -> List<MetadataDTO>
    default List<MetadataDTO> toDTOs(List<Metadata> metadataList) {
        return metadataList.stream()
                .map(this::toMetadataDTO) // Her Metadata nesnesini MetadataDTO'ya dönüştür
                .collect(Collectors.toList()); // Dönüştürülmüş nesneleri listeye ekle
    }
    Metadata toMetadataEntity(MetadataDTO metadataDTO); // DTO'dan Entity'ye dönüşüm
}
