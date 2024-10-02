package com.cms.spring.jpa.postgresql.postgresql.service;

import com.cms.spring.jpa.postgresql.postgresql.model.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IMDBService {

    private final String apiUrl = "https://api.imdb.com/some-endpoint";

    public Metadata getMetadataFromIMDB(String imdbId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/metadata/" + imdbId;
        return restTemplate.getForObject(url, Metadata.class);
    }
}
