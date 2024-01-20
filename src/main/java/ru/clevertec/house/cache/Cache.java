package ru.clevertec.house.cache;

public interface Cache<K, V> {

    void capacity(int capacity);

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    void clear();
}
