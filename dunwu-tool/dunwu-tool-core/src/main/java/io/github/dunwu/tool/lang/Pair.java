package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.clone.CloneSupport;

import java.io.Serializable;

/**
 * 键值对对象，只能在构造时传入键值
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author looly
 * @since 4.1.5
 */
public class Pair<K, V> extends CloneSupport<Pair<K, V>> implements Serializable {

    private static final long serialVersionUID = 1L;

    private K key;

    private V value;

    /**
     * 构造
     *
     * @param key   键
     * @param value 值
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair [key=" + key + ", value=" + value + "]";
    }

    /**
     * 获取键
     *
     * @return 键
     */
    public K getKey() {
        return this.key;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public V getValue() {
        return this.value;
    }

}
