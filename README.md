# Dynamic_CachingSystem
Objective: Design and implement a dynamic multilevel caching system that efficiently manages data across multiple cache levels. The system should support dynamic addition of cache levels, eviction policies, and data retrieval across these levels.

## Overview

This project implements a flexible, multi-level cache system in Java. It supports dynamic addition and removal of cache levels, each with configurable size and eviction policies (LRU or LFU). The system is designed to be thread-safe and efficient for concurrent access.

## Features

- Multiple cache levels with configurable sizes
- Support for LRU (Least Recently Used) and LFU (Least Frequently Used) eviction policies
- Dynamic addition and removal of cache levels
- Thread-safe operations using read-write locks
- Automatic data promotion to higher cache levels on access

## Structure

The project consists of the following main classes:

1. DynamicCacheSystem: The main class that manages multiple cache levels.
2. CacheLevel: Represents a single level of cache with a specific size and eviction policy.
3. EvictionPolicy: An interface for implementing different eviction policies.
4. LRUPolicy: Implementation of the Least Recently Used eviction policy.
5. LFUPolicy: Implementation of the Least Frequently Used eviction policy.

## Usage

To use the Dynamic Cache System:

1. Create an instance of DynamicCacheSystem:

java
DynamicCacheSystem cache = new DynamicCacheSystem();


2. Add cache levels with desired sizes and eviction policies:
   java
cache.addCacheLevel(100, "LRU"); // Add L1 cache with size 100 and LRU policy
cache.addCacheLevel(500, "LFU"); // Add L2 cache with size 500 and LFU policy


3. Use the cache to get and put values:

java
String value = cache.get("key1"); // Get a value (fetches from memory if not in cache)
cache.put("key2", "value2"); // Put a value in the cache


4. Display the contents of all cache levels:

java
cache.displayCache();


5. Remove a cache level if needed:

java
cache.removeCacheLevel(1); // Remove the L2 cache
## Thread Safety

The DynamicCacheSystem uses ReadWriteLock to ensure thread-safe operations. Multiple threads can read from the cache concurrently, while write operations (such as adding or removing cache levels) are exclusive.

## Extending the System

To add a new eviction policy:

1. Implement the EvictionPolicy interface.
2. Modify the CacheLevel constructor to support the new policy type.

## Performance Considerations

- The system automatically promotes frequently accessed data to higher cache levels, improving access times for hot data.
- The LFU policy provides better performance for scenarios with stable access patterns, while LRU works well for recency-based access patterns.
- Adding or removing cache levels are expensive operations and should be done sparingly.

## Future Improvements

- Implement size-aware eviction to handle variable-sized cache entries.
- Add support for time-based expiration of cache entries.
- Implement cache statistics and monitoring capabilities.

