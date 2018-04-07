package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

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
        marque =(getIntent().getExtras().getString("Marque"));
        Offre.setmModeleRecherche(modele);
        modele = (getIntent().getExtras().getString("Modele"));
        Offre.setmMarqueRecherche(marque);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(marque + " " + modele);
        rv = (RecyclerView) findViewById(R.id.rvMarques);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
    }

        public void fetchData(){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://159.203.34.137:80/api/v1/offers/")
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
                            offreListe = new ArrayList<Offre>();
                            JSONObject jsonResponse = new JSONObject(response.body().string());
                            JSONArray array = jsonResponse.getJSONArray("content");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                JSONObject objChild = (JSONObject) obj.get("model");
                                String nameBrand = objChild.getString("name");
                                JSONObject objChildBrand = (JSONObject) objChild.get("brand");
                                String nameModel = objChildBrand.getString("name");
                                if (nameBrand.equals(modele)){
                                    String prix = obj.getString("price");
                                    offreListe.add(new Offre(prix, nameBrand, nameModel, obj.getString("id")));
                                }
                            }
                            for (int i = 0; i < Offre.mOffres.size(); i++){
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    OffreListeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (offreListe.size() == 0){
                                Toast.makeText(getApplicationContext(), "Aucune annonce pour ce modele, veuillez en choisir un autre!",
                                        Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                            rv.setLayoutManager(new LinearLayoutManager(mContext));
                            OffreAdapter adapter = new OffreAdapter(mContext, offreListe);
                            rv.setAdapter(adapter);
                        }
                    });
                }
            });
    }
}
