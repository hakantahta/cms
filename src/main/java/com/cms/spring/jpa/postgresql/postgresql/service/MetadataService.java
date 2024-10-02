package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import com.cms.spring.jpa.postgresql.postgresql.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetadataService {

    private MetadataRepository metadataRepository;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public List<Metadata> getAllMetadata() {
        return metadataRepository.findAll();
    }

    public Optional<Metadata> getMetadataById(Long id) {
        return metadataRepository.findById(id);
    }

    public Metadata createMetadata(Metadata metadata) {
        return metadataRepository.save(metadata);
    }

    public Optional<Metadata> updateMetadata(Long id, Metadata metadataDetails) {
        Optional<Metadata> metadata = metadataRepository.findById(id);
        if (metadata.isPresent()) {
            Metadata existingMetadata = metadata.get();
            existingMetadata.setTitle(metadataDetails.getTitle());
            existingMetadata.setPlot(metadataDetails.getPlot());
            existingMetadata.setYear(metadataDetails.getYear());
            existingMetadata.setLanguage(metadataDetails.getLanguage());
            existingMetadata.setCountry(metadataDetails.getCountry());
            return Optional.of(metadataRepository.save(existingMetadata));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteMetadata(Long id) {
        if (metadataRepository.existsById(id)) {
            metadataRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
