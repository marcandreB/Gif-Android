package ima.ulaval.ca.tp3_final;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private UnitOffre currentOffre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mOffreID =  getIntent().getExtras().getString("ID");
        Log.d("id", mOffreID.toString());
        getTV();
        fetchData();



    }
    private void getTV(){
        this.TVMocdel = this.findViewById(R.id.TVVModele);
        this.TVMarque  = this.findViewById(R.id.TVVMarque);
        this.TVDescription = this.findViewById(R.id.TVVDescription);
        this.TVPrice = this.findViewById(R.id.TVVPrice);
        this.TVSeller = this.findViewById(R.id.TVVSeller);
        this.TVTransmission = this.findViewById(R.id.TVVTransmission);
        this.TVCreated = this.findViewById(R.id.TVVCreated);
        this.TVYear = this.findViewById(R.id.TVVYear);
        this.TVUpdated = this.findViewById(R.id.TVVUpdated);
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

                            TVMarque.setText(mMarque);
                            TVMocdel.setText(mModele);
                            TVDescription.setText(mDescription);
                            TVCreated.setText(mCreated);
                            TVPrice.setText(mPrice);
                            TVSeller.setText(mSeller);
                            TVTransmission.setText(mTransmission);
                            TVUpdated.setText(mUpdated);
                            TVYear.setText(mYear);
                            Picasso.get().load(mImage).into(IVImage);
                        }
                    });
                }
            }
        });
    }


}

