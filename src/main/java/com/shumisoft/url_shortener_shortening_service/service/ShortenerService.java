package com.shumisoft.url_shortener_shortening_service.service;

import java.net.URISyntaxException;

public interface ShortenerService {

    public String getHomePage();

    public String getNewB62ID();

    public String shortenUrl(String url);

    public String cleanURL(String url) throws URISyntaxException;

}
