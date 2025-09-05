package com.shumisoft.url_shortener_shortening_service.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class IdGenerator {

    private long curId;
    private final long endId;

    public synchronized long nextId() {
        if (curId >= endId) {
            log.info("Range Exhausted.");
            throw new RuntimeException("ID range exhausted! Need new range from Consul.");
        }
        return curId++;
    }

}
