import java.util.*;

/**
 * Implements the Least Frequently Used (LFU) eviction policy.
 * This policy evicts the item that has been accessed the least number of times.
 */
public class LFUPolicy implements EvictionPolicy {
    private Map<String, Integer> frequencies; // Tracks the access frequency of each key
    private Map<Integer, LinkedHashSet<String>> lists; // Groups keys by their access frequency
    private int minFreq; // The minimum frequency among all keys

    public LFUPolicy() {
        frequencies = new HashMap<>();
        lists = new HashMap<>();
        minFreq = 0;
    }
   /**
     * Updates the access frequency of a key.
     *
     * @param key The key that was accessed
     */
    @Override
    public void accessKey(String key) {
        int freq = frequencies.getOrDefault(key, 0);
        frequencies.put(key, freq + 1);
        if (freq > 0) {
            lists.get(freq).remove(key);
            if (freq == minFreq && lists.get(freq).isEmpty()) {
                minFreq++;
            }
        } else {
            minFreq = 1;
        }
        lists.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
    }
  /**
     * Determines which key should be evicted based on the LFU policy.
     *
     * @return The key with the lowest access frequency
     */
    @Override
    public String evict() {
        String leastFrequent = lists.get(minFreq).iterator().next();
        lists.get(minFreq).remove(leastFrequent);
        frequencies.remove(leastFrequent);
        if (lists.get(minFreq).isEmpty()) {
            lists.remove(minFreq);
            minFreq++;
        }
        return leastFrequent;
    }
}
