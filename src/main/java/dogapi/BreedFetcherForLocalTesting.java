package dogapi;

public final class BreedFetcherForLocalTesting implements BreedFetcher {

    @Override
    public java.util.List<String> getSubBreeds(String breed)
            throws BreedFetcher.BreedNotFoundException {

        if (breed == null) {
            throw new BreedFetcher.BreedNotFoundException("null");
        }

        switch (breed.toLowerCase()) {
            case "hound":
                // 两个子品种 → MainTest 里期望 size = 2
                return java.util.List.of("afghan", "basset");
            case "poodle":
                // 给几个随便的子品种，方便本地手动测
                return java.util.List.of("miniature", "standard", "toy");
            default:
                // 其余一律视为不存在 → 让 Main 返回 0
                throw new BreedFetcher.BreedNotFoundException(breed);
        }
    }
}
