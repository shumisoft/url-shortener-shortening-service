package com.shumisoft.url_shortener_shortening_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumisoft.url_shortener_shortening_service.service.ShortenerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class ShortenerController {
    private ShortenerService service;

    @GetMapping
    public ResponseEntity<String> getHomePage() {
        return ResponseEntity.ok(service.getHomePage());
    }

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.shortenUrl(url));
    }

}
