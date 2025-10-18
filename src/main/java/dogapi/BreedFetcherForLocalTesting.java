package dogapi;

import java.util.List;

public final class BreedFetcherForLocalTesting implements BreedFetcher {

    private int callCount = 0;

    @Override
    public List<String> getSubBreeds(String breed)
            throws BreedFetcher.BreedNotFoundException {

        callCount++;  // 记录每次被调用（测试会检查这个数）

        if (breed == null) {
            throw new BreedFetcher.BreedNotFoundException("null");
        }

        switch (breed.toLowerCase()) {
            case "hound":
                // 有 2 个子品种，测试期望 getNumberOfSubBreeds("hound", mock) == 2
                return List.of("afghan", "basset");
            case "poodle":
                return List.of("miniature", "standard", "toy");
            default:
                // 非法品种：抛异常（CachingBreedFetcher 不应缓存异常）
                throw new BreedFetcher.BreedNotFoundException(breed);
        }
    }

    // 供测试断言调用次数
    public int getCallCount() {
        return callCount;
    }
}
