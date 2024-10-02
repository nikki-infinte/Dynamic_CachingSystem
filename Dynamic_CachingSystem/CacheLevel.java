import java.util.HashMap;
import java.util.Map;

public class CacheLevel {
    private final int capacity;//size
    private Map<String, String> cache;//{key:value}
    private EvictionPolicy evictionPolicy;//{LRU |LFU}

    //constructor
    public CacheLevel(int capacity, String policyType) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.evictionPolicy = policyType.equalsIgnoreCase("LRU") ? new LRUPolicy() : new LFUPolicy();
    }


     /**
     * Retrieves a value from the cache.
     *
     * @param key The key of the entry to retrieve
     * @return The value associated with the key, or null if the key is not in the cache
     */

    public String get(String key) {
        if (cache.containsKey(key)) {
            evictionPolicy.accessKey(key);
            return cache.get(key);
        }
        return null;
    }

    
    /**
     * Puts a key-value pair into the cache.
     * If the cache is at capacity and the key doesn't exist, an entry is evicted.
     *
     * @param key The key of the entry to add
     * @param value The value to associate with the key
     */
    public void put(String key, String value) {
        if (cache.size() >= capacity && !cache.containsKey(key)) {
            String evictedKey = evictionPolicy.evict();
            cache.remove(evictedKey);
        }
        cache.put(key, value);
        evictionPolicy.accessKey(key);
    }
    
    /**
     * Returns a copy of the current cache contents.
     *
     * @return A new HashMap containing all current cache entries
     */
    public Map<String, String> getContents() {
        return new HashMap<>(cache);
    }
}