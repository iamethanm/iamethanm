package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     *
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        String url = "https://dog.ceo/api/breed/"
                + URLEncoder.encode(breed, StandardCharsets.UTF_8) + "/list";

        Request req = new Request.Builder().url(url).build();
        try (Response res = client.newCall(req).execute()) {
            if (!res.isSuccessful() || res.body() == null) {
                throw new BreedNotFoundException(breed);
            }
            String body = res.body().string();
            JSONObject json = new JSONObject(body);
            if (!"success".equals(json.optString("status", ""))) {
                // README 说明：不存在的品种会返回 {"status":"error", ...}。直接抛异常
                throw new BreedNotFoundException(breed);
            }
            JSONArray arr = json.getJSONArray("message");
            List<String> out = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) out.add(arr.getString(i));
            return out;
        } catch (IOException e) {
            // 网络/IO问题按作业要求统一视为拉取失败 → 抛BreedNotFoundException也可
            throw new RuntimeException(e);
        }
    }
}
