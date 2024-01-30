//package ru.clevertec.house.util;
//
//import lombok.Builder;
//import ru.clevertec.house.cache.Cache;
//import ru.clevertec.house.cache.impl.CacheLfu;
//
//@Builder(setterPrefix = "with")
//public class CacheLfuTestData {
//
//    public Cache<Integer, String> getCache() {
//        Cache<Integer, String> cache = new CacheLfu<>();
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//        return cache;
//    }
//}
