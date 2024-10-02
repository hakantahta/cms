package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.responses.CastResponse;
import com.cms.spring.jpa.postgresql.postgresql.service.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/castt")
public class CastController {

    //@Autowired yerine constructor ınjection kullandık
    private CastService castService;
    public CastController(CastService castService) {
        this.castService = castService;
    }

    // Tüm oyuncuları getirme
    @GetMapping
    public ResponseEntity<List<Cast>> getAllCasts() {
        try {
            List<Cast> casts = castService.getAllCasts();
            return new ResponseEntity<>(casts, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ID'ye göre oyuncu getirme
    @GetMapping("/{id}")
    public ResponseEntity<CastResponse> getCastById(@PathVariable Long id) {
        Optional<Cast> cast = castService.getCastById(id);
        if (cast.isPresent()) {
            CastResponse response = castService.toCastResponse(cast.get()); // Entity to response mapping
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Yeni oyuncu ekleme
    @PostMapping("/manual")
    public ResponseEntity<Cast> createCast(@RequestBody Cast cast) {
        try {
            Cast savedCast = castService.createCast(cast);
            return new ResponseEntity<>(savedCast, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Oyuncu güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<Cast> updateCast(@PathVariable Long id, @RequestBody Cast castDetails) {
        Optional<Cast> updatedCast = castService.updateCast(id, castDetails);
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
