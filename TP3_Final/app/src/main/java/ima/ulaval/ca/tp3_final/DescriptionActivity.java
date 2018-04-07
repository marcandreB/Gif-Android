package ima.ulaval.ca.tp3_final;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DescriptionActivity extends AppCompatActivity {
    private String mOffreID;
    private String mModele;
    private String mMarque;
    private String mDescription;
    private String mYear;
    private String mTransmission;
    private String mSeller;
    private String mPrice;
    private String mCreated;
    private String mUpdated;
    private String mImage;
    private ImageView IVImage;
    private TextView TVMocdel;
    private TextView TVMarque;
    private TextView TVDescription;
    private TextView TVYear;
    private TextView TVTransmission;
    private TextView TVSeller;
    private TextView TVPrice;
    private TextView TVCreated;
    private TextView TVUpdated;

    private Toolbar mToolbar;
    private UnitOffre currentOffre;
    ArrayList<Offre> offres = new ArrayList<Offre>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        this.mOffreID =  getIntent().getExtras().getString("ID");
        getSupportActionBar().setTitle("Offre numero : " + mOffreID);
        getTV();
        if (!this.mOffreID.equals("nouveau")) {
            fetchData();
        }
        else{
            getLastOffre();
        }

    }

    private void getLastOffre() {
        OkHttpClient client = new OkHttpClient();
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("seller", "111120022")
                .addFormDataPart("offer", "true")
                .build();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/offers/search/")
                .post(body)
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
                        offres = new ArrayList<Offre>();
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String id = obj.getString("id");
                            JSONObject objChild = (JSONObject) obj.get("model");
                            String nameBrand = objChild.getString("name");
                            JSONObject objChildBrand = (JSONObject) objChild.get("brand");
                            String nameModel = objChildBrand.getString("name");
                            String prix = obj.getString("price");
                            offres.add(new Offre(prix, nameBrand, nameModel, id));
                        }
                        int maxID = 1;
                        for (int i = 0; i < offres.size(); i++){
                            Offre currentOffre = offres.get(i);
                            if (Integer.parseInt(currentOffre.getID()) > maxID) {
                                maxID = Integer.parseInt(currentOffre.getID());
                            }
                        }
                        mOffreID = Integer.toString(maxID);
                        fetchData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    private void getTV(){
        this.TVMocdel = this.findViewById(R.id.TVModele);
        this.TVMarque  = this.findViewById(R.id.TVMarque);
        this.TVDescription = this.findViewById(R.id.TVDescription);
        this.TVPrice = this.findViewById(R.id.TVPrice);
        this.TVSeller = this.findViewById(R.id.TVSeller);
        this.TVTransmission = this.findViewById(R.id.TVTransmission);
        this.TVCreated = this.findViewById(R.id.TVCreated);
        this.TVYear = this.findViewById(R.id.TVYear);
        this.TVUpdated = this.findViewById(R.id.TVUpdated);
        this.IVImage = this.findViewById(R.id.IVImage);
    }

    public void fetchData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/offers/" + mOffreID)
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
                        //Get the object
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        //Get the model
                        JSONObject obj = (JSONObject) jsonResponse.get("content");
                        JSONObject objModel = (JSONObject) obj.get("model");
                        JSONObject objChild = (JSONObject) objModel.get("brand");
                        mModele = objChild.getString("name");
                        Log.d("model", mModele);
                        //Get the brand
                        mMarque = objModel.getString("name");
                        //get the rest
                        mYear = obj.getString("year");
                        mDescription = obj.getString("description");
                        mPrice = obj.getString("price");
                        mTransmission = obj.getString("transmission");
                        mSeller = obj.getString("seller");
                        mCreated = obj.getString("created");
                        mUpdated = obj.getString("updated");
                        mImage = obj.getString("image");
                        currentOffre = new UnitOffre(mMarque,mModele,mDescription,mPrice,mTransmission,mCreated,mUpdated,mSeller,mYear, mImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // Display the requested data on UI in main thread
                    DescriptionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String sourceString = "<b>" + "Marque : " + "</b> " + mMarque;
                            TVMarque.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Modele : " + "</b> " + mModele;
                            TVMocdel.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Description : " + "</b> " + mDescription;
                            TVDescription.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Annee : " + "</b> " + mYear;
                            TVYear.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Prix : " + "</b> " + mPrice;
                            TVPrice.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Transmission : " + "</b> " + mTransmission;
                            TVTransmission.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Vendeur : " + "</b> " + mSeller;
                            TVSeller.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Creation : " + "</b> " + mCreated.substring(0,10);
                            TVCreated.setText(Html.fromHtml(sourceString));

                            sourceString = "<b>" + "Mise a jour : " + "</b> " + mUpdated.substring(0,10);
                            TVUpdated.setText(Html.fromHtml(sourceString));
                            mToolbar.setTitle("Offre numero : " + mOffreID);
                            Picasso.get().load(mImage).into(IVImage);
                        }
                    });
                }
            }
        });
    }


}

