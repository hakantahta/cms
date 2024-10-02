package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleCastResponse;
import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleMetadataResponse;
import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import com.cms.spring.jpa.postgresql.postgresql.repository.CastRepository;
import com.cms.spring.jpa.postgresql.postgresql.repository.ContentRepository;
import com.cms.spring.jpa.postgresql.postgresql.repository.MetadataRepository;
import com.cms.spring.jpa.postgresql.postgresql.responses.CastResponse;
import com.cms.spring.jpa.postgresql.postgresql.responses.ContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentService {


    private ContentRepository contentRepository;
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


    private MetadataRepository metadataRepository;
    public MetadataRepository getMetadataRepository() {
        return metadataRepository;
    }


    private CastRepository castRepository;
    public CastRepository getCastRepository() {
        return castRepository;
    }


    private IMDBService imdbService;
    public IMDBService getImdbService() {
        return imdbService;
    }

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    public Content createContentManual(Metadata metadata) {
        Metadata savedMetadata = metadataRepository.save(metadata);
        Content content = new Content();
        content.setMetadata(savedMetadata);
        return contentRepository.save(content);
    }

    public CastResponse addCastToContent(CastResponse castResponse, Long contentId) throws Exception {
        Optional<Content> contentOpt = contentRepository.findById(contentId);
        if (contentOpt.isPresent()) {
            Cast cast = new Cast();
            cast.setName(castResponse.getName());
            cast.setPoster(castResponse.getPoster());

            Content content = contentOpt.get();
            content.getActors().add(cast);
            cast.getContents().add(content);

            castRepository.save(cast);
            contentRepository.save(content);

            CastResponse response = new CastResponse();
            response.setId(cast.getId());
            response.setName(cast.getName());
            response.setPoster(cast.getPoster());

            return response;
        } else {
            throw new Exception("Content not found");
        }
    }

    public Content createContentFromIMDB(String title) {
        Metadata imdbMetadata = imdbService.getMetadataFromIMDB(title);
        Content content = new Content();
        content.setMetadata(imdbMetadata);
        return contentRepository.save(content);
    }

    public Content updateContent(Long id, Metadata metadata) throws Exception {
        Optional<Content> existingContent = contentRepository.findById(id);
        if (existingContent.isPresent()) {
            Content content = existingContent.get();
            Metadata updatedMetadata = metadataRepository.save(metadata);
            content.setMetadata(updatedMetadata);
            return contentRepository.save(content);
        } else {
            throw new Exception("Content not found");
        }
    }

    public void deleteContent(Long id) throws Exception {
        if (contentRepository.existsById(id)) {
            contentRepository.deleteById(id);
        } else {
            throw new Exception("Content not found");
        }
    }

    public ContentResponse toContentResponse(Content content) {
        ContentResponse response = new ContentResponse();
        response.setId(content.getId());
        response.setDirector(content.getDirector());
        response.setCreatedAt(content.getCreatedAt().toString());

        // Metadata'yı SimpleMetadataResponse ile dönüştürüyoruz
        SimpleMetadataResponse metadataResponse = new SimpleMetadataResponse();
        metadataResponse.setId(content.getMetadata().getId());
        metadataResponse.setTitle(content.getMetadata().getTitle());
        response.setMetadata(metadataResponse);

        // Cast'ları SimpleCastResponse ile dönüştürüyoruz
        List<SimpleCastResponse> actors = content.getActors().stream()
                .map(cast -> new SimpleCastResponse(cast.getId(), cast.getName()))
                .collect(Collectors.toList());
        response.setActors(actors);

        return response;
    }
}
