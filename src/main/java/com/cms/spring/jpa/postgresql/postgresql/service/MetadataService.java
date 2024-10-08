package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.DTO.MetadataDTO;
import com.cms.spring.jpa.postgresql.postgresql.config.MetadataMapper;
import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import com.cms.spring.jpa.postgresql.postgresql.repository.MetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetadataService {
    private static final Logger logger = LoggerFactory.getLogger(MetadataService.class);
    private final MetadataRepository metadataRepository;
    private final MetadataMapper metadataMapper;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository, MetadataMapper metadataMapper) {
        this.metadataRepository = metadataRepository;
        this.metadataMapper = metadataMapper;
    }

    // Tüm metadata'ları DTO olarak getirme
    public List<Metadata> getAllMetadata() {
        return metadataRepository.findAll(); // Tüm Metadata entity'lerini döndür
    }


    // ID'ye göre metadata'yı DTO olarak getirme
    public Optional<MetadataDTO> getMetadataById(Long id) {
        Optional<Metadata> metadata = metadataRepository.findById(id);
        return metadata.map(metadataMapper::toMetadataDTO); // Entity'yi DTO'ya dönüştür
    }

    // Yeni metadata ekleme (DTO'yu kullanarak)
    public MetadataDTO createMetadata(MetadataDTO metadataDTO) {
        Metadata metadataEntity = metadataMapper.toMetadataEntity(metadataDTO); // DTO'yu entity'ye dönüştür
        Metadata savedMetadata = metadataRepository.save(metadataEntity); // Entity'yi veritabanına kaydet
        return metadataMapper.toMetadataDTO(savedMetadata); // Kaydedilen entity'yi DTO'ya dönüştür
    }

    // Metadata güncelleme (DTO'ya göre)
    public Optional<MetadataDTO> updateMetadata(Long id, MetadataDTO metadataDTO) {
        Optional<Metadata> existingMetadata = metadataRepository.findById(id);
        if (existingMetadata.isPresent()) {
            Metadata metadataEntity = metadataMapper.toMetadataEntity(metadataDTO);
            metadataEntity.setId(id); // Güncellenen entity'nin ID'sini ayarla
            Metadata updatedMetadata = metadataRepository.save(metadataEntity);
            return Optional.of(metadataMapper.toMetadataDTO(updatedMetadata)); // Güncellenen entity'yi DTO'ya dönüştür
        }
        return Optional.empty();
    }

    // Metadata silme
    public boolean deleteMetadata(Long id) {
        if (metadataRepository.existsById(id)) {
            metadataRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
