import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DynamicCacheSystem {
    private List<CacheLevel> cacheLevels; // List of cache levels, ordered from highest to lowest priority
    private ReadWriteLock lock; // Lock for ensuring thread-safe operations

    /**
     * Constructs a new DynamicCacheSystem with no initial cache levels.
     */
    public DynamicCacheSystem() {
        this.cacheLevels = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Adds a new cache level to the system.
     *
     * @param size The capacity of the new cache level
     * @param evictionPolicy The eviction policy for the new cache level ("LRU" or "LFU")
     */
    public void addCacheLevel(int size, String evictionPolicy) {
        lock.writeLock().lock();
        try {
            cacheLevels.add(new CacheLevel(size, evictionPolicy));
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Retrieves a value from the cache system.
     * If the value is found in a lower-level cache, it is promoted to higher levels.
     *
     * @param key The key to look up
     * @return The value associated with the key, or a newly fetched value if not in cache
     */
    public String get(String key) {
        lock.readLock().lock();
        try {
            for (int i = 0; i < cacheLevels.size(); i++) {
                String value = cacheLevels.get(i).get(key);
                if (value != null) {
                    // Move the data to higher cache levels
                    for (int j = i - 1; j >= 0; j--) {
                        cacheLevels.get(j).put(key, value);
                    }
                    return value;
                }
            }
            // Simulate fetching from main memory
            String value = "Fetched_" + key;
            put(key, value);
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Puts a key-value pair into the highest priority cache level.
     *
     * @param key The key to store
     * @param value The value to associate with the key
     */
    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            if (!cacheLevels.isEmpty()) {
                cacheLevels.get(0).put(key, value);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes a cache level from the system.
     *
     * @param level The index of the cache level to remove
     */
    public void removeCacheLevel(int level) {
        lock.writeLock().lock();
        try {
            if (level >= 0 && level < cacheLevels.size()) {
                cacheLevels.remove(level);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Displays the contents of all cache levels.
     */
    public void displayCache() {
        lock.readLock().lock();
        try {
            for (int i = 0; i < cacheLevels.size(); i++) {
                System.out.println("L" + (i + 1) + " Cache: " + cacheLevels.get(i).getContents());
            }
        } finally {
            lock.readLock().unlock();
        }
    }
}
