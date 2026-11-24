import java.util.*;

public class Main {
    record Entry<V>(V val, long expMs) {}
    static class TtlLruCache<K, V> {
        private final int maxSize;
        private final LinkedHashMap<K, Entry<V>> map;
        TtlLruCache(int maxSize) {
            this.maxSize = maxSize;
            this.map = new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<K, Entry<V>> e) { return size() > TtlLruCache.this.maxSize; }
            };
        }
        void put(K k, V v, long nowMs, long ttlMs) { map.put(k, new Entry<>(v, nowMs + ttlMs)); }
        V get(K k, long nowMs) {
            Entry<V> e = map.get(k);
            if (e == null) return null;
            if (nowMs > e.expMs()) { map.remove(k); return null; }
            return e.val();
        }
    }
    public static void main(String[] args) {
        TtlLruCache<String, Integer> c = new TtlLruCache<>(2);
        c.put("a", 1, 0, 100); c.put("b", 2, 0, 100); c.get("a", 10); c.put("c", 3, 10, 100);
        if (c.get("b", 10) != null || !Integer.valueOf(1).equals(c.get("a", 50)) || c.get("a", 150) != null)
            throw new AssertionError("Cache failed");
        System.out.println("PASS 07-ttl-lru-cache");
    }
}
