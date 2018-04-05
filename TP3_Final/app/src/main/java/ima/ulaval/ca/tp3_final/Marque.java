package ima.ulaval.ca.tp3_final;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Marque {
    private String mName;
    public Marque(String name) {
        mName = name;
    }
    public String getName() {
        return mName;
    }
    public ArrayList<Marque> getMarqueListe(){
        return marqueListe;
    }
    private static ArrayList<Marque> marqueListe = new ArrayList<>();

    public static ArrayList<Marque> createMarqueList(RecyclerView rv, Context cont) {
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
                    response.priorResponse();
                } else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        for (int i = 0; i < array.length(); i++) {
                           Marque nouvelleMarque = new Marque(array.getJSONObject(i).getString("name"));
                           marqueListe.add(nouvelleMarque);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(cont));
        MarqueAdapter adapter = new MarqueAdapter(cont, marqueListe);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return marqueListe;
    }
}
