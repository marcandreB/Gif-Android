package ima.ulaval.ca.tp3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Marques {

    public static final List<MarqueItem> ITEMS = new ArrayList<MarqueItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, MarqueItem> ITEM_MAP = new HashMap<String, MarqueItem>();

    private static final int COUNT = 25;

    static {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/brands/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("Probleme", "onresponsefail");
                } else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        String[] marque = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            marque[i] = array.getJSONObject(i).getString("name");
                        }
                        for (int i = 0; i< marque.length ; i++){
                            addItem(createDummyItem(i, marque[i]));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static void addItem(MarqueItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MarqueItem createDummyItem(int position, String marque) throws IOException {

        return new MarqueItem(String.valueOf(position), marque, marque);
    }


    public static class MarqueItem {
        public final String id;
        public final String content;
        final String details;

        MarqueItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
