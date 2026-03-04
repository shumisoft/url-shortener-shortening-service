package com.shumisoft.url_shortener_shortening_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shumisoft.url_shortener_shortening_service.entity.UrlMap;

@Repository
public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {

    @Query("SELECT u.b62encoded FROM UrlMap u")
    List<String> findAllB62encoded();

}
