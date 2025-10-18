package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DogApiBreedFetcher implements BreedFetcher {
    private static final String BASE = "https://dog.ceo/api/breed/";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getSubBreeds(String breed) throws BreedFetcher.BreedNotFoundException {
        // 用全限定名避免 import 问题
        String url = BASE
                + java.net.URLEncoder.encode(breed, java.nio.charset.StandardCharsets.UTF_8)
                + "/list";

        Request req = new Request.Builder().url(url).build();
        try (Response res = client.newCall(req).execute()) {
            if (!res.isSuccessful() || res.body() == null) {
                throw new BreedFetcher.BreedNotFoundException(breed);
            }
            String body = Objects.requireNonNull(res.body()).string();
            JSONObject json = new JSONObject(body);
            if (!"success".equals(json.optString("status", ""))) {
                throw new BreedFetcher.BreedNotFoundException(breed);
            }
            JSONArray arr = json.getJSONArray("message");
            List<String> out = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) out.add(arr.getString(i));
            return out;
        } catch (IOException e) {
            // 网络问题按作业约定不区分，统一视作取不到
            throw new RuntimeException(e);
        }
    }
}
