package com.shumisoft.url_shortener_shortening_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlMap {
    @Id
    @GeneratedValue()
    private Long id;

    private String b62encoded;

    private String url;
}
