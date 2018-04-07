package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Modele {
    private static String mMarque;
    private String mID;
    private String mName;
    public static String getMarque(){
        return mMarque;
    }
    public String getName(){
        return mName;
    }
    public String getID(){
        return mID;
    }
    public static void setMarque(String marque){
        mMarque = marque;
    }
    public Modele(String name, String ID){
        this.mName = name;
        this.mID = ID;
    }
    @Override
    public String toString(){
        return mName;
    }
    public static  ArrayList<Modele> mModeles = new ArrayList<>();
    public static ArrayList<Modele> createModeleList() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/models/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    response.priorResponse();
                } else {
                    try {
                        for (int i = 0; i < mModeles.size(); i++) {
                            mModeles.remove(i);
                        }
                        if (mModeles.size() > 0) Log.d("Lol", Integer.toString(mModeles.size()));
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            JSONObject objChild = (JSONObject) obj.get("brand");
                            String name = objChild.getString("name");
                            String ID = objChild.getString("id");
                            if (name.equals(mMarque)) {
                                String mod = obj.getString("name");
                                mModeles.add(new Modele(mod, ID));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return mModeles;
    }
}