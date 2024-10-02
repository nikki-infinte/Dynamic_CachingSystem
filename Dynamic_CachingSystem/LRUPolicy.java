import java.util.LinkedHashSet;

/**
 * Implements the Least Recently Used (LRU) eviction policy.
 * This policy evicts the item that hasn't been accessed for the longest time.
 */
public class LRUPolicy implements EvictionPolicy {
    private LinkedHashSet<String> order; // Maintains the order of key accesses

    /**
     * Constructs a new LRUPolicy instance.
     */
    public LRUPolicy() {
        this.order = new LinkedHashSet<>();
    }

    /**
     * Updates the access order of a key.
     *
     * @param key The key that was accessed
     */
    @Override
    public void accessKey(String key) {
        order.remove(key);
        order.add(key);
    }

    /**
     * Determines which key should be evicted based on the LRU policy.
     *
     * @return The least recently used key
     */
    @Override
    public String evict() {
        String leastUsed = order.iterator().next();
        order.remove(leastUsed);
        return leastUsed;
    }
}
