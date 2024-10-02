package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import com.cms.spring.jpa.postgresql.postgresql.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/metadata")
public class MetadataController {

    private MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    // Tüm metadata'yı getir
    @GetMapping
    public ResponseEntity<List<Metadata>> getAllMetadata() {
        try {
            List<Metadata> metadataList = metadataService.getAllMetadata();
            return new ResponseEntity<>(metadataList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ID'ye göre metadata getir
    @GetMapping("/{id}")
    public ResponseEntity<Metadata> getMetadataById(@PathVariable Long id) {
        Optional<Metadata> metadata = metadataService.getMetadataById(id);
        return metadata.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Yeni metadata ekle
    @PostMapping
    public ResponseEntity<Metadata> createMetadata(@RequestBody Metadata metadata) {
        try {
            Metadata savedMetadata = metadataService.createMetadata(metadata);
            return new ResponseEntity<>(savedMetadata, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Metadata güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<Metadata> updateMetadata(@PathVariable Long id, @RequestBody Metadata metadataDetails) {
        Optional<Metadata> updatedMetadata = metadataService.updateMetadata(id, metadataDetails);
        return updatedMetadata.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Metadata silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetadata(@PathVariable Long id) {
        try {
            boolean isDeleted = metadataService.deleteMetadata(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
