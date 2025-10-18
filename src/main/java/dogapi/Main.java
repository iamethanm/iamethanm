package dogapi;

public final class Main {
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            return breedFetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }
}

    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
    try {
        return breedFetcher.getSubBreeds(breed).size();
    } catch (BreedFetcher.BreedNotFoundException e) {
        return 0; // 无效品种时按测试预期返回 0
    }
}
