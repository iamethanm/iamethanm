package dogapi;

import java.util.*;

public final class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher inner;
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
        callsMade++;
        List<String> r = inner.getSubBreeds(breed); // 若这里抛异常 → 不缓存
        cache.put(breed, List.copyOf(r));
        return r;
    }

    public int getCallsMade() {
        return callsMade;
    }
}
