/**
 * Interface for implementing cache eviction policies.
 * Different eviction strategies can be implemented by classes that implement this interface.
 */
public interface EvictionPolicy {
    /**
     * Updates the policy's internal state when a key is accessed.
     *
     * @param key The key that was accessed
     */
    void accessKey(String key);

    /**
     * Determines which key should be evicted from the cache.
     *
     * @return The key that should be evicted
     */
    String evict();
}
