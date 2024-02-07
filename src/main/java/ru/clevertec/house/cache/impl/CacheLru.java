package ru.clevertec.house.cache.impl;

import ru.clevertec.house.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hibernate.internal.util.collections.CollectionHelper.LOAD_FACTOR;
import static ru.clevertec.house.constant.Constant.CAPACITY_FACTOR;

/**
 * Реализация LRU-кэша.
 * Этот класс представляет собой реализацию LRU-кэша (Least Recently Used),
 * который позволяет хранить пары ключ-значение в кэше с определенной вместимостью.
 * Механизм работы кэша основан на вытеснении наименее недавно используемых элементов.
 * Кэш имеет фиксированную вместимость, которая задается при создании объекта класса.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 * @author Витикова Мария
 */

public class CacheLru<K, V> implements Cache<K, V> {

    /**
     * Вместимость кэша
     */
    private int capacity;

    /**
     * Карта для хранения элементов кэша
     */
    private Map<K, V> cache;


    /**
     * Устанавливает вместимость кэша.
     *
     * @param capacity вместимость кэша
     */
    @Override
    public void capacity(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>((int) (capacity * CAPACITY_FACTOR), LOAD_FACTOR);
    }

    /**
     * Получает значение из кэша по ключу.
     * Если элемент отсутствует, возвращает null.
     * Перемещает найденный элемент в конец списка элементов для обновления LRU.
     *
     * @param key ключ
     * @return значение, соответствующее ключу, или null, если элемент отсутствует
     */
    @Override
    public V get(Object key) {
        K keyCasted = (K) key;
        V value = cache.remove(keyCasted);
        if (value == null) {
            return null;
        }
        cache.put(keyCasted, value);
        return value;
    }

    /**
     * Добавляет элемент в кэш или обновляет его, если он уже существует.
     * Возвращает старое значение элемента, или null, если элемент добавлен впервые.
     * Если вместимость кэша превышена, вытесняет наименее недавно использованный элемент.
     *
     * @param key   ключ
     * @param value значение
     * @return старое значение элемента, или null, если элемент добавлен впервые
     */
    @Override
    public V put(K key, V value) {
        V currentValue = cache.get(key);
        if (currentValue == null) {
            if (cache.size() >= capacity) {
                cache.remove(cache.keySet().iterator().next());
            }
        } else {
            cache.remove(key);
        }
        cache.put(key, value);
        return currentValue;
    }

    /**
     * Удаляет элемент из кэша по ключу.
     * Возвращает значение удаленного элемента, или null, если элемент отсутствует.
     *
     * @param key ключ
     * @return значение удаленного элемента, или null, если элемент отсутствует
     */
    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    /**
     * Очищает кэш, удаляя все элементы
     */
    @Override
    public void clear() {
        cache.clear();
    }
}