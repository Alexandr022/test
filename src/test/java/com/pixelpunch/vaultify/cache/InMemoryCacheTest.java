package com.pixelpunch.vaultify.cache;

import com.pixelpunch.vaultify.core.cache.InMemoryCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCacheTest {

    @InjectMocks
    private InMemoryCache<Long, String> inMemoryCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPutAndGet() {
        Long key = 1L;
        String value = "Test Value";

        inMemoryCache.put(key, value);
        String retrievedValue = inMemoryCache.get(key);

        assertEquals(value, retrievedValue);
    }

    @Test
    void testContainsKey() {
        Long key = 1L;
        String value = "Test Value";

        inMemoryCache.put(key, value);

        assertTrue(inMemoryCache.containsKey(key));
    }

    @Test
    void testRemove() {
        Long key = 1L;
        String value = "Test Value";

        inMemoryCache.put(key, value);
        inMemoryCache.remove(key);

        assertNull(inMemoryCache.get(key));
    }

    @Test
    void testSize() {
        Long key1 = 1L;
        Long key2 = 2L;
        String value = "Test Value";

        inMemoryCache.put(key1, value);
        inMemoryCache.put(key2, value);

        assertEquals(2, inMemoryCache.getSize());
    }
}

