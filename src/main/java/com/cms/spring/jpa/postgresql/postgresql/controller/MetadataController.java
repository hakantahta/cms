package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.DTO.MetadataDTO;
import com.cms.spring.jpa.postgresql.postgresql.config.MetadataMapper;
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

    private final MetadataService metadataService;
    private final MetadataMapper metadataMapper;

    // Constructor injection ile MetadataService ve MetadataMapper'ı alıyoruz
    public MetadataController(MetadataService metadataService, MetadataMapper metadataMapper) {
        this.metadataService = metadataService;
        this.metadataMapper = metadataMapper;
    }

    // Tüm metadata'yı getir
    @GetMapping
    public ResponseEntity<List<MetadataDTO>> getAllMetadata() {
        try {
            // Servisten Metadata entity listesini alın
            List<Metadata> metadataList = metadataService.getAllMetadata();
            // Metadata entity listesini DTO'ya dönüştürmek için mapper kullanın
            List<MetadataDTO> metadataDTOList = metadataMapper.toDTOs(metadataList);
            return new ResponseEntity<>(metadataDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    // ID'ye göre metadata getir
    @GetMapping("/{id}")
    public ResponseEntity<MetadataDTO> getMetadataById(@PathVariable Long id) {
        Optional<MetadataDTO> metadata = metadataService.getMetadataById(id);
        return metadata.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Yeni metadata ekle
    @PostMapping
    public ResponseEntity<MetadataDTO> createMetadata(@RequestBody MetadataDTO metadataDTO) {
        try {
            MetadataDTO savedMetadata = metadataService.createMetadata(metadataDTO);
            return new ResponseEntity<>(savedMetadata, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Metadata güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<MetadataDTO> updateMetadata(@PathVariable Long id, @RequestBody MetadataDTO metadataDTO) {
        Optional<MetadataDTO> updatedMetadata = metadataService.updateMetadata(id, metadataDTO);
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
