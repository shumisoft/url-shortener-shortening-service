package com.shumisoft.url_shortener_shortening_service.utils;

import java.util.Base64;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.kv.model.PutParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class IdRangeAllocator {
    private final ConsulClient consulClient;
    private static final String KEY = "config/url-shortener-shortening-service,default/id-sequence";
    private static final long RANGE_SIZE = 1000; // allocate 1000 IDs at once

    public IdGenerator allocateRange() {
        while (true) {
            // 1. Read the current value
            Response<GetValue> response = consulClient.getKVValue(KEY);
            GetValue value = response.getValue();

            long current = (value == null || value.getValue() == null)
                    ? 0
                    : Long.parseLong(new String(Base64.getDecoder().decode(value.getValue())));

            long newValue = current + 1;

            // 2. CAS update
            long modifyIndex = (value == null) ? 0 : value.getModifyIndex();

            PutParams params = new PutParams();
            params.setCas(modifyIndex);

            boolean success = consulClient
                    .setKVValue(KEY, String.valueOf(newValue), params)
                    .getValue();

            if (success) {
                log.info("Acquired range Successfully: {} - {}", current * RANGE_SIZE, newValue * RANGE_SIZE);
                return new IdGenerator(current * RANGE_SIZE, newValue * RANGE_SIZE);
            }

            // Someone else grabbed it → retry
            try {
                log.info("Failed to acquire range. Retry after 50ms.");
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
                log.info("Ignoring Interruption.");
            }
        }

    }

}
