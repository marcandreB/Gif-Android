package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class ModeleActivity extends AppCompatActivity {

    private ArrayList<Modele> modeles;
    private RecyclerView rv;
    private String marque;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modele);
        mContext = getApplicationContext();
        Modele.setMarque(getIntent().getExtras().getString("marque"));
        marque = getIntent().getExtras().getString("marque");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(marque);
        rv = (RecyclerView) findViewById(R.id.rvMarques);
        fetchData();
    }

    public void fetchData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/models/")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Do something when request failed
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Error : " + response);
                }else {
                    modeles = new ArrayList<Modele>();
                    JSONObject jsonResponse = null;
                    try {
                        jsonResponse = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = jsonResponse.getJSONArray("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = null;
                        try {
                            obj = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject objChild = null;
                        try {
                            objChild = (JSONObject) obj.get("brand");
                            String id;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String name = null;
                        try {
                            name = objChild.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (name.equals(marque)) {
                            String mod = null;
                            try {
                                mod = obj.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String id = null;
                            try {
                                id = objChild.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            modeles.add(new Modele(mod,id));
                        }
                    }
                }

                // Display the requested data on UI in main thread
                ModeleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        ModeleAdapter adapter = new ModeleAdapter(mContext, modeles);
                        rv.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
