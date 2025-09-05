package com.shumisoft.url_shortener_shortening_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shumisoft.url_shortener_shortening_service.entity.UrlMap;

public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {

}
