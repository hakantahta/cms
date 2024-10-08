package com.cms.spring.jpa.postgresql.postgresql.config;

import com.cms.spring.jpa.postgresql.postgresql.DTO.CastDTO;
import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Mapper(componentModel = "spring")
public interface CastMapper {

    // Cast'ten CastDTO'ya dönüşüm
    CastDTO toCastDTO(Cast castt); // İsim güncellendi

    // List<Cast> listesinden List<CastDTO> listesine dönüşüm
    default List<CastDTO> toDTOs(List<Cast> casts) {
        return casts.stream()
                .map(this::toCastDTO) // Her Cast nesnesini CastDTO'ya dönüştür
                .collect(Collectors.toList()); // Java 16 ve sonrası için toList() kullanıyoruz
    }

    // CastDTO'dan Cast'e dönüşüm
    Cast toCastEntity(CastDTO castDTO); // İsim güncellendi

    // List<CastDTO> listesinden List<Cast> listesine dönüşüm
    List<Cast> toEntities(List<CastDTO> castDTOs); // Yeni metot
}
