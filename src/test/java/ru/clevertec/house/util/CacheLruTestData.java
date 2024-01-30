//package ru.clevertec.house.util;
//
//import lombok.Builder;
//import ru.clevertec.house.cache.Cache;
//import ru.clevertec.house.cache.impl.CacheLfu;
//import ru.clevertec.house.cache.impl.CacheLru;
//
//@Builder(setterPrefix = "with")
//public class CacheLruTestData {
//
//    public Cache<Integer, String> getCache() {
//        Cache<Integer, String> cache = new CacheLru<>();
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//        return cache;
//    }
//}
