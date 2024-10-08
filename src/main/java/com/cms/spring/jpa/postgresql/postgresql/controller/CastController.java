package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.DTO.CastDTO;
import com.cms.spring.jpa.postgresql.postgresql.service.CastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/castt")
public class CastController {

    private final CastService castService;

    public CastController(CastService castService) {
        this.castService = castService;
    }

    // Tüm oyuncuları getirme
    @GetMapping
    public ResponseEntity<List<CastDTO>> getAllCasts() {
        try {
            List<CastDTO> casts = castService.getAllCasts();
            return new ResponseEntity<>(casts, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ID'ye göre oyuncu getirme
    @GetMapping("/{id}")
    public ResponseEntity<CastDTO> getCastById(@PathVariable Long id) {
        Optional<CastDTO> cast = castService.getCastById(id);
        return cast.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Yeni oyuncu ekleme
    @PostMapping
    public ResponseEntity<CastDTO> createCast(@RequestBody CastDTO castDTO) {
        try {
            CastDTO savedCast = castService.createCast(castDTO);
            return new ResponseEntity<>(savedCast, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Oyuncu güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<CastDTO> updateCast(@PathVariable Long id, @RequestBody CastDTO castDTO) {
        Optional<CastDTO> updatedCast = castService.updateCast(id, castDTO);
        return updatedCast.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Oyuncu silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCast(@PathVariable Long id) {
        try {
            boolean isDeleted = castService.deleteCast(id);
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
