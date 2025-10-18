package dogapi;

public class Main {

    /**
     * Return the number of sub-breeds for the given breed.
     * If the breed is unknown, return 0 instead of throwing.
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            return breedFetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }

    // Optional demo entrypoint (tests won't use this).
    public static void main(String[] args) {
        // no-op
    }
}

     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
    try {
        return breedFetcher.getSubBreeds(breed).size();
    } catch (BreedFetcher.BreedNotFoundException e) {
        return 0; // 无效品种时按测试预期返回 0
    }
}
