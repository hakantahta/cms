package com.cms.spring.jpa.postgresql.postgresql.controller;

import com.cms.spring.jpa.postgresql.postgresql.DTO.ContentDTO;
import com.cms.spring.jpa.postgresql.postgresql.DTO.CastDTO;
import com.cms.spring.jpa.postgresql.postgresql.config.ContentMapper;
import com.cms.spring.jpa.postgresql.postgresql.model.Content;
import com.cms.spring.jpa.postgresql.postgresql.service.ContentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

	private final ContentService contentService;
	private final ContentMapper contentMapper;

	public ContentController(ContentService contentService, ContentMapper contentMapper) {
		this.contentService = contentService;
		this.contentMapper = contentMapper;
	}

	// Tüm içerikleri getiren GET endpoint'i
	@GetMapping
	public ResponseEntity<List<ContentDTO>> getAllContents() {
		try {
			List<ContentDTO> contentDTOs = contentService.getAllContents(); // ContentDTO listesini al
			return new ResponseEntity<>(contentDTOs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ID'ye göre içerik getiren GET endpoint'i
	@GetMapping("/{id}")
	public ResponseEntity<ContentDTO> getContentById(@PathVariable Long id) {
		Optional<ContentDTO> content = contentService.getContentById(id);
		return content.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Manuel veri ekleme
	@PostMapping("/manual")
	public ResponseEntity<ContentDTO> createContentManual(@RequestBody ContentDTO contentDTO) {
		try {
			ContentDTO createdContent = contentService.createContentManual(contentDTO);
			return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	// Cast ekleme
	@PostMapping("/{contentId}/cast")
	public ResponseEntity<CastDTO> addCastToContent(@PathVariable Long contentId, @RequestBody CastDTO castDTO) {
		try {
			CastDTO response = contentService.addCastToContent(castDTO, contentId);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Content güncelleme
	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO> updateContent(@PathVariable Long id, @RequestBody ContentDTO contentDTO) {
		try {
			ContentDTO updatedContent = contentService.updateContent(id, contentDTO);
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
