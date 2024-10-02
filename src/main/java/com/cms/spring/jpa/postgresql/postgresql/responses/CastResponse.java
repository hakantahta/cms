package com.cms.spring.jpa.postgresql.postgresql.responses;

import com.cms.spring.jpa.postgresql.postgresql.SimpleResponse.SimpleContentResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CastResponse {
    private Long id; // Oyuncu ID'si
    private String name; // Oyuncu adı
    private String poster; // Oyuncu poster resmi

    // İlişkili content bilgilerini ekleyebiliriz ama burada sadece id ve title olsun
    private List<SimpleContentResponse> contents;
}
