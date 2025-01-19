package com.shumisoft.url_shortener_shortening_service.service.impl;

import java.util.List;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.shumisoft.url_shortener_shortening_service.repository.UrlMapRepository;
import com.shumisoft.url_shortener_shortening_service.service.BloomFilterService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloomFilterServiceImpl implements BloomFilterService {

    private final UrlMapRepository repository;

    private final RedissonClient redissonClient;
    private RBloomFilter<String> b62encodedStringBloomFilter;

    private static final String FILTER_NAME = "url-shortener-bloom-filter";
    private static final long EXPECTED_INSERTIONS = 1_000_000L; // Expected user base
    private static final double FALSE_PROBABILITY = 0.01; // 1% false positive rate

    @Override
    @PostConstruct
    public void init() {
        this.b62encodedStringBloomFilter = redissonClient.getBloomFilter(FILTER_NAME);

        boolean isNew = b62encodedStringBloomFilter.tryInit(EXPECTED_INSERTIONS, FALSE_PROBABILITY);

        if (isNew) {
            log.info("Bloom Filter created.");
            hydrate();
        }
    }

    private void hydrate() {
        log.info("Hydrating B62encoded strings from the DB.");
        List<String> usernames = repository.findAllB62encoded();
        usernames.stream().forEach(this::add);
        log.info("Hydration completed. Count: {}", usernames.size());
    }

    @Override
    public boolean add(String username) {
        return b62encodedStringBloomFilter.add(username);
    }
}