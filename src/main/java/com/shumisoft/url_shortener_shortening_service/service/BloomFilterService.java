package com.shumisoft.url_shortener_shortening_service.service;

public interface BloomFilterService {

  void init();

  boolean add(String username);

}