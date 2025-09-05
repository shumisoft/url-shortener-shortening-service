package com.shumisoft.url_shortener_shortening_service.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.text.html.HTML;

import org.springframework.stereotype.Service;

import com.shumisoft.url_shortener_shortening_service.entity.UrlMap;
import com.shumisoft.url_shortener_shortening_service.exception.InvalidUrlException;
import com.shumisoft.url_shortener_shortening_service.page.HomePage;
import com.shumisoft.url_shortener_shortening_service.repository.UrlMapRepository;
import com.shumisoft.url_shortener_shortening_service.service.ShortenerService;
import com.shumisoft.url_shortener_shortening_service.utils.IdGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShortenerServiceImpl implements ShortenerService {

    private static final int CHARACTER_LENGTH = 6;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private IdGenerator generator;
    private UrlMapRepository repository;

    @Override
    public String getNewB62ID() {
        long id = generator.nextId();

        StringBuilder result = new StringBuilder();

        for (long i = 0; i < CHARACTER_LENGTH; i++) {
            result.append(BASE62.charAt((int) (id % 62)));
            id /= 64;
        }

        return result.reverse().toString();

    }

    @Override
    public String shortenUrl(String url) {

        try {
            url = cleanURL(url);

        } catch (URISyntaxException e) {
            throw new InvalidUrlException("Invalid Url");
        }

        UrlMap urlMap = UrlMap.builder().b62encoded(getNewB62ID()).url(url).build();
        urlMap = repository.save(urlMap);

        return urlMap.getB62encoded();
    }

    @Override
    public String cleanURL(String url) throws URISyntaxException {
        URI uri = new URI(url.trim());

        String scheme = (uri.getScheme() == null) ? "http" : uri.getScheme().toLowerCase();
        String host = uri.getHost() != null ? uri.getHost().toLowerCase() : "";
        int port = uri.getPort();

        // Remove default ports (80 for http, 443 for https)
        String portPart = "";
        if (port != -1 && !((scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443))) {
            portPart = ":" + port;
        }

        // Normalize path
        String path = (uri.getPath() == null || uri.getPath().isEmpty()) ? "/" : uri.getPath();

        // Rebuild cleaned URL
        return scheme + "://" + host + portPart + path;
    }

    @Override
    public String getHomePage() {
        return HomePage.html;
    }

}
