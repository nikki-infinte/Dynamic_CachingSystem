public class Main {
    public static void main(String[] args) {
        DynamicCacheSystem cacheSystem = new DynamicCacheSystem();

        cacheSystem.addCacheLevel(3, "LRU");
        cacheSystem.addCacheLevel(2, "LFU");

        cacheSystem.put("A", "1");
        cacheSystem.put("B", "2");
        cacheSystem.put("C", "3");

        System.out.println(cacheSystem.get("A")); // Returns "1" from L1

        cacheSystem.put("D", "4"); // L1 is full, evicts least recently used

        System.out.println(cacheSystem.get("C")); // Fetches from L2 and promotes to L1

        cacheSystem.displayCache();

        // Additional test cases
        cacheSystem.removeCacheLevel(1); // Remove L2 cache
        System.out.println("After removing L2 cache:");
        cacheSystem.displayCache();

        cacheSystem.addCacheLevel(2, "LRU"); // Add new L2 cache
        System.out.println("After adding new L2 cache:");
        cacheSystem.displayCache();

        System.out.println(cacheSystem.get("B")); // Should fetch from main memory
        cacheSystem.displayCache();
    }
}
    

