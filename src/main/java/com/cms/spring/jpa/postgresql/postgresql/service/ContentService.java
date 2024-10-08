package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.DTO.CastDTO;
import com.cms.spring.jpa.postgresql.postgresql.DTO.ContentDTO;
import com.cms.spring.jpa.postgresql.postgresql.config.CastMapper;
import com.cms.spring.jpa.postgresql.postgresql.config.ContentMapper;
import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.repository.CastRepository;
import com.cms.spring.jpa.postgresql.postgresql.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;
    private final CastRepository castRepository;
    private final ContentMapper contentMapper;
    private final CastMapper castMapper;

    public ContentService(ContentRepository contentRepository, CastRepository castRepository,
                          ContentMapper contentMapper, CastMapper castMapper) {
        this.contentRepository = contentRepository;
        this.castRepository = castRepository;
        this.contentMapper = contentMapper;
        this.castMapper = castMapper;
    }

    // Tüm içerikleri getir
    public List<ContentDTO> getAllContents() {
        List<Content> contents = contentRepository.findAll(); // veya aşağıdaki gibi bir sorgu kullanabilirsiniz
         //List<Content> contents = contentRepository.findAllWithCasts(); // özel bir sorgu
        return contentMapper.toDTOs(contents);
    }


    // ID'ye göre içerik getir
    public Optional<ContentDTO> getContentById(Long id) {
        return contentRepository.findById(id).map(contentMapper::toDTO);
    }

    // İçeriği manuel ekleme
    public ContentDTO createContentManual(ContentDTO contentDTO) {
        Content content = contentMapper.toEntity(contentDTO); // DTO'yu entity'ye dönüştür
        Content savedContent = contentRepository.save(content); // Veritabanına kaydet
        return contentMapper.toDTO(savedContent); // Entity'den DTO'ya dönüşüm
    }

    // İçeriği güncelleme
    public ContentDTO updateContent(Long id, ContentDTO contentDTO) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            content.setDirector(contentDTO.getDirector());
            contentRepository.save(content); // İçeriği güncelle ve kaydet
            return contentMapper.toDTO(content); // Güncellenmiş entity'yi DTO'ya dönüştür
        } else {
            throw new RuntimeException("Content not found with id: " + id);
        }
    }

    // İçerik silme
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

    // Cast ekleme metodu
    public CastDTO addCastToContent(CastDTO castDTO, Long contentId) {
        Optional<Content> contentOptional = contentRepository.findById(contentId);

        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            Cast cast = castMapper.toCastEntity(castDTO); // DTO'dan entity'ye dönüşüm

            // Cast'i kaydet ve content ile ilişkilendir
            castRepository.save(cast);
            content.getCasts().add(cast); // Cast içeriğe ekleniyor

            // Bu noktada content_cast tablosuna veriyi eklemek için content'i de kaydetmelisiniz.
            contentRepository.save(content);

            return castMapper.toCastDTO(cast); // Cast'i DTO'ya çevir ve döndür
        } else {
            throw new RuntimeException("Content not found with id: " + contentId);
        }
    }
}
