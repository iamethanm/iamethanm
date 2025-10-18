package dogapi;

public final class Main {
    private Main() {}

    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            return breedFetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }
}
