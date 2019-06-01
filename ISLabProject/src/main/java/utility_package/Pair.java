package utility_package;

public class Pair<K, V> {
    private final K key;
    private final V value;

    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<>(element0, element1);
    }
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }
}