//package ru.clevertec.house.cache;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.clevertec.house.config.CacheConfig;
//import ru.clevertec.house.util.CacheLfuTestData;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
////@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {CacheConfig.class})
//public class CacheLfuTest {
//
//    @Test
//    public void getByIdShouldReturnExpectedValueByKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        String value = cache.get(2);
//        assertEquals("two", value);
//    }
//
//    @Test
//    public void getByIdShouldReturnNullByNoExistingKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        String value = cache.get(4);
//        assertNull(value);
//    }
//
//    @Test
//    public void putShouldReturnAddedValueByNewKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        String oldValue = cache.put(1, "one");
//        assertNotNull(oldValue);
//        assertEquals("one", cache.get(1));
//    }
//
//    @Test
//    public void putShouldReturnUpdatedValueByKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        cache.put(1, "one");
//        String oldValue = cache.put(1, "newOne");
//        assertEquals("one", oldValue);
//        assertEquals("newOne", cache.get(1));
//    }
//
//    @Test
//    public void removeRemovesValueByKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        cache.put(1, "one");
//        String removedValue = cache.remove(1);
//        assertEquals("one", removedValue);
//        assertNull(cache.get(1));
//    }
//
//    @Test
//    public void removeShouldReturnNullByNoExistingKey() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        cache.put(1, "one");
//        String removedValue = cache.remove(2);
//        assertNotNull(removedValue);
//        assertEquals("one", cache.get(1));
//    }
//
//    @Test
//    public void clearTest() {
//        Cache<Integer, String> cache = CacheLfuTestData.builder().build().getCache();
//        cache.clear();
//        assertNull(cache.get(1));
//        assertNull(cache.get(2));
//        assertNull(cache.get(3));
//    }
//}