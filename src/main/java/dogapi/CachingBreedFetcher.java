package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher inner;                 // 被包装的真实 fetcher
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.inner = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (cache.containsKey(breed)) {
            return List.copyOf(cache.get(breed));
        }
        // 真正调用一次，计数+1
        callsMade++;
        List<String> result = inner.getSubBreeds(breed); // 若这里抛异常 → 不缓存，直接向外抛
        cache.put(breed, List.copyOf(result));
        return result;
    }

    public int getCallsMade() {
        return callsMade;
    }
}
