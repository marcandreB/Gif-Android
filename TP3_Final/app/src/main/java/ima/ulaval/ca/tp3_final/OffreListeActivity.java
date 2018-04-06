package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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

public class OffreListeActivity extends AppCompatActivity {

    private String marque;
    private String modele;
    private ArrayList<Offre> offreListe;
    private Context mContext;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offre_liste);
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
        marque =(getIntent().getExtras().getString("Marque"));
        Offre.setmModeleRecherche(modele);
        modele = (getIntent().getExtras().getString("Modele"));
        Offre.setmMarqueRecherche(marque);
        rv = (RecyclerView) findViewById(R.id.rvMarques);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
    }

        public void fetchData(){
        OkHttpClient client = new OkHttpClient();

        // Initialize a new Request
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/offers/")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Do something when request failed
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    response.priorResponse();
                } else {
                    try {
                        offreListe = new ArrayList<Offre>();
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            Integer ID = obj.getInt("id");
                            Log.d("id :" , ID.toString());
                            JSONObject objChild = (JSONObject) obj.get("model");
                            String nameBrand = objChild.getString("name");
                            JSONObject objChildBrand = (JSONObject) objChild.get("brand");
                            String nameModel = objChildBrand.getString("name");
                            if (nameBrand.equals(modele)) {
                                String prix = obj.getString("price");
                                offreListe.add(new Offre(prix, nameBrand, nameModel, ID));
                                Log.d("ajoute", "heh");
                            }

                        }
                        Log.d("Nombre", Integer.toString(offreListe.size()));
                        for (int i = 0; i < offreListe.size(); i++) {
                            Log.d("Offre" + Integer.toString(i), offreListe.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // Display the requested data on UI in main thread
                    OffreListeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ok", "heh");
                            OffreAdapter adapter = new OffreAdapter(mContext, offreListe);
                            rv.setAdapter(adapter);
                        }
                    });
                }
             }
        });
    }


}
