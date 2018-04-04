package ima.ulaval.ca.tp3;

import android.app.Activity;
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


public class Modeles {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<Modeles.ModeleItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Modeles.ModeleItem> ITEM_MAP = new HashMap<String, Modeles.ModeleItem>();

    private static final int COUNT = 25;

    static {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/models/")
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
                        Log.d("oups", "Entre!");
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        String[] modelesArray = new String[array.length()];
                        int currentIndex = 0;
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject marque = array.getJSONObject(i).getJSONObject("brand");/*
                            if (marque.getString("name") == ModeleActivity.getMarque()) {
                                Log.d("oupsii", marque.getString("name"));
                                modelesArray[currentIndex++] = array.getJSONObject(i).getString("name");
                            }
                            */
                            modelesArray[currentIndex++] = array.getJSONObject(i).getString("name");
                        }

                        for (int i = 0; i< modelesArray.length ; i++){
                            addItem(createModeleItem(i, modelesArray[i]));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static void addItem(Modeles.ModeleItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Modeles.ModeleItem createModeleItem(int position, String modele) throws IOException {

        return new Modeles.ModeleItem(String.valueOf(position), modele, modele);
    }

    private static String makeDetails(int position ) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ModeleItem {
        public final String id;
        public final String content;
        public final String details;

        public ModeleItem(String id, String content, String details) {
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

