package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleContentResponse;
import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.repository.CastRepository;
import com.cms.spring.jpa.postgresql.postgresql.responses.CastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    // Tüm oyuncuları getirme
    public List<Cast> getAllCasts() {
        return castRepository.findAll();
    }

    // ID'ye göre oyuncu getirme
    public Optional<Cast> getCastById(Long id) {
        return castRepository.findById(id);
    }

    // Yeni oyuncu ekleme
    public Cast createCast(Cast cast) {
        return castRepository.save(cast);
    }

    // Oyuncu güncelleme
    public Optional<Cast> updateCast(Long id, Cast castDetails) {
        Optional<Cast> existingCast = castRepository.findById(id);
        if (existingCast.isPresent()) {
            Cast cast = existingCast.get();
            cast.setName(castDetails.getName());
            cast.setPoster(castDetails.getPoster());
            return Optional.of(castRepository.save(cast));
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

    //Sadece oyuncunun adını ve resmini getirmeyi sağlayan response sınıfında kullandığımız toCastRespnse sınınfının referansı olan metod
    public CastResponse toCastResponse(Cast cast) {
        CastResponse response = new CastResponse();
        response.setId(cast.getId());
        response.setName(cast.getName());
        response.setPoster(cast.getPoster());

        // Content'leri SimpleContentResponse olarak ekle
        List<SimpleContentResponse> contents = cast.getContents().stream()
                .map(content -> new SimpleContentResponse(content.getId(), content.getDirector()))
                .collect(Collectors.toList());
        response.setContents(contents);

        return response;
    }




}
