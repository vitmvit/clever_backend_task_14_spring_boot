package ru.clevertec.house.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.house.cache.Cache;
import ru.clevertec.house.cache.impl.CacheLfu;
import ru.clevertec.house.cache.impl.CacheLru;
import ru.clevertec.house.exception.CacheNotFoundException;
import ru.clevertec.house.model.dto.parent.UuidDto;
import ru.clevertec.house.service.proxy.HouseProxyService;
import ru.clevertec.house.service.proxy.PersonProxyService;

import java.util.UUID;

import static ru.clevertec.house.constant.Constant.LFU;
import static ru.clevertec.house.constant.Constant.LRU;

/**
 * Класс, который отвечает за конфигурацию кэша.
 */
@Configuration
public class CacheConfig {

    /**
     * Алгоритм кэширования.
     */
    @Value("${cache.algorithm}")
    private String algorithm;

    /**
     * Вместимость кэша.
     */
    @Value("${cache.capacity}")
    private int capacity;

    /**
     * Возвращает кэш LRU.
     *
     * @return кэш LRU.
     */
    @Bean
    public Cache<UUID, UuidDto> getLruCache() {
        return new CacheLru<>();
    }

    /**
     * Возвращает кэш LFU.
     *
     * @return кэш LFU.
     */
    @Bean
    public Cache<UUID, UuidDto> getLfuCache() {
        return new CacheLfu<>();
    }

    /**
     * Возвращает кэш в зависимости от выбранного алгоритма.
     *
     * @return кэш.
     * @throws CacheNotFoundException если выбран некорректный алгоритм кэширования.
     */
    @Bean
    public Cache<UUID, UuidDto> cache() {
        Cache<UUID, UuidDto> cache;
        if (algorithm.equals(LFU)) {
            cache = getLfuCache();
        } else if (algorithm.equals(LRU)) {
            cache = getLruCache();
        } else {
            throw new CacheNotFoundException();
        }
        cache.capacity(capacity);
        return cache;
    }

    /**
     * Возвращает сервис прокси для работы с HouseDto.
     *
     * @return HouseProxyService - сервис прокси для работы с HouseDto.
     */
    @Bean
    public HouseProxyService getHouseProxyService() {
        return new HouseProxyService(cache());
    }

    /**
     * Возвращает сервис прокси для работы с PersonDto.
     *
     * @return PersonProxyService - сервис прокси для работы с PersonDto.
     */
    @Bean
    public PersonProxyService getPersonProxyService() {
        return new PersonProxyService(cache());
    }
}
