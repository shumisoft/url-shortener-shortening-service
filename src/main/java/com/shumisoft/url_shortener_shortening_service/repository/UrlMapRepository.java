package com.shumisoft.url_shortener_shortening_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shumisoft.url_shortener_shortening_service.entity.UrlMap;

@Repository
public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {

}
