package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.DTO.CastDTO;
import com.cms.spring.jpa.postgresql.postgresql.config.CastMapper;
import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.repository.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private CastMapper castMapper; // DTO'lar ile Entity'ler arasında dönüşümü sağlayan mapper

    // Tüm oyuncuları DTO olarak getirme
    public List<CastDTO> getAllCasts() {
        List<Cast> castEntities = castRepository.findAll();
        return castEntities.stream()
                .map(castMapper::toCastDTO) // Entity'leri DTO'lara dönüştür
                .collect(Collectors.toList());
    }

    // ID'ye göre oyuncuyu DTO olarak getirme
    public Optional<CastDTO> getCastById(Long id) {
        Optional<Cast> cast = castRepository.findById(id);
        return cast.map(castMapper::toCastDTO); // Entity'yi DTO'ya dönüştür
    }

    // Yeni oyuncu ekleme (DTO'yu kullanarak)
    public CastDTO createCast(CastDTO castDTO) {
        Cast castEntity = castMapper.toCastEntity(castDTO); // DTO'yu entity'ye dönüştür
        Cast savedCast = castRepository.save(castEntity); // Entity'yi veritabanına kaydet
        return castMapper.toCastDTO(savedCast); // Kaydedilen entity'yi DTO'ya dönüştür
    }

    // Oyuncu güncelleme (DTO'ya göre)
    public Optional<CastDTO> updateCast(Long id, CastDTO castDTO) {
        Optional<Cast> existingCast = castRepository.findById(id);
        if (existingCast.isPresent()) {
            Cast castEntity = castMapper.toCastEntity(castDTO);
            castEntity.setId(id); // Güncellenen entity'nin ID'sini ayarla
            Cast updatedCast = castRepository.save(castEntity);
            return Optional.of(castMapper.toCastDTO(updatedCast)); // Güncellenen entity'yi DTO'ya dönüştür
        }
        return Optional.empty();
    }

    // Oyuncu silme
    public boolean deleteCast(Long id) {
        if (castRepository.existsById(id)) {
            castRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
