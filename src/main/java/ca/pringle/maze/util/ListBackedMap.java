package ca.pringle.maze.util;

import ca.pringle.maze.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ListBackedMap<K, V> {

    private final Map<K, List<V>> map;

    ListBackedMap(final Map<K, List<V>> map) {
        this.map = map;
        Preconditions.checkNotNull(map);
    }

    public ListBackedMap() {
        this(new HashMap<>());
    }

    public void put(final K key,
                    final V value) {

        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(value);
    }

    public List<V> get(final K key) {
        return map.get(key) == null ? new ArrayList<>() : map.get(key);
    }

    public int size() {
        return map.size();
    }

    public void remove(final K key,
                       final V value) {

        if (!map.containsKey(key)) {
            return;
        }

        map.get(key).remove(value);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
