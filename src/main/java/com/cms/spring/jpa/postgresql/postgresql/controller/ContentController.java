package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.model.Cast;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import com.cms.spring.jpa.postgresql.postgresql.repository.CastRepository;
import com.cms.spring.jpa.postgresql.postgresql.repository.ContentRepository;
import com.cms.spring.jpa.postgresql.postgresql.repository.MetadataRepository;
import com.cms.spring.jpa.postgresql.postgresql.responses.CastResponse;
import com.cms.spring.jpa.postgresql.postgresql.service.ContentService;
import com.cms.spring.jpa.postgresql.postgresql.service.IMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

	private final ContentService contentService;  // ContentService için final tanımlama
	private final ContentRepository contentRepository;
	private final IMDBService imdbService;
	private final MetadataRepository metadataRepository;
	private final CastRepository castRepository;

	@Autowired // Spring'e bağımlılıkları enjekte etmesini sağlamak için
	public ContentController(ContentService contentService,
							 ContentRepository contentRepository,
							 IMDBService imdbService,
							 MetadataRepository metadataRepository,
							 CastRepository castRepository) {
		this.contentService = contentService;
		this.contentRepository = contentRepository;
		this.imdbService = imdbService;
		this.metadataRepository = metadataRepository;
		this.castRepository = castRepository;
	}

	// Tüm içerikleri getiren GET endpoint'i
	@GetMapping
	public ResponseEntity<List<Content>> getAllContents() {
		try {
			List<Content> contents = contentService.getAllContents();
			return new ResponseEntity<>(contents, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ID'ye göre içerik getiren GET endpoint'i
	@GetMapping("/{id}")
	public ResponseEntity<Content> getContentById(@PathVariable Long id) {
		Optional<Content> content = contentService.getContentById(id);
		return content.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Manuel veri ekleme
	@PostMapping("/manual")
	public ResponseEntity<Content> createContentManual(@RequestBody Metadata metadata) {
		try {
			Content content = contentService.createContentManual(metadata);
			return new ResponseEntity<>(content, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cast ekleme
	@PostMapping("/manual/cast")
	public ResponseEntity<CastResponse> addCastToContent(@RequestBody CastResponse castResponse, @RequestParam Long contentId) {
		try {
			CastResponse response = contentService.addCastToContent(castResponse, contentId);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// IMDb API'den veri çekme
	@PostMapping("/fetch")
	public ResponseEntity<Content> createContentFromIMDB(@RequestBody String title) {
		try {
			Content content = contentService.createContentFromIMDB(title);
			return new ResponseEntity<>(content, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Content güncelleme
	@PutMapping("/{id}")
	public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Metadata metadata) {
		try {
			Content updatedContent = contentService.updateContent(id, metadata);
			return new ResponseEntity<>(updatedContent, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Content silme
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
		try {
			contentService.deleteContent(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
