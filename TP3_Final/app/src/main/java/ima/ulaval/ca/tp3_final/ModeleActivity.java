package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        Modele.setMarque(getIntent().getExtras().getString("Marque"));
        marque = getIntent().getExtras().getString("Marque");
        rv = (RecyclerView) findViewById(R.id.rvMarques);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //rv.setLayoutManager(new LinearLayoutManager(this));
        //Modele.createModeleList();
        fetchData();
    }

    public void updateUi(ArrayList<Modele> mod){
        modeles = mod;
        rv.setLayoutManager(new LinearLayoutManager(this));
        ModeleAdapter adapter = new ModeleAdapter(this, modeles);
        rv.setAdapter(adapter);
    }

    public void fetchData(){
        OkHttpClient client = new OkHttpClient();

        // Initialize a new Request
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
                    Log.d("Lol", "yaaaaaaao");
                    modeles = new ArrayList<Modele>();
                    Log.d("Lol", "yaaaaaaao");
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
                    Log.d("marque", marque);
                    Log.d("modele size avant for", Integer.toString(modeles.size()));
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
                            Log.d("Hehxvxe!", mod);
                            Log.d("modele size", Integer.toString(modeles.size()));
                        }
                    }
                }


                // Display the requested data on UI in main thread
                ModeleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ok", "heh");
                        ModeleAdapter adapter = new ModeleAdapter(mContext, modeles);
                        rv.setAdapter(adapter);
                    }
                });
            }
        });
    }



}
