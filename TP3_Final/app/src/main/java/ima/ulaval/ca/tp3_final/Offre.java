package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.media.Image;
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

/**
 * Created by Meh on 2018-04-04.
 */

public class Offre {
    private Image mImage;
    private String mPrix;
    private String mMarque;
    private String mModele;

    private static String mMarqueRecherche;
    private static String mModeleRecherche;


    public Offre(String pPrix, String pMarque, String pModele){
        this.mPrix = pPrix;
        this.mMarque = pMarque;
        this.mModele = pModele;
    }
    public static ArrayList<Offre> mOffres = new ArrayList<>();
    public void setName(Image pImage){
        this.mImage = pImage;
    }
    public void setmPrix(String pPrix){
        this.mPrix =pPrix;
    }
    public void setModele(String pModele){
        this.mModele = pModele;
    }
    public void setMarque(String pMarque){
        this.mMarque = pMarque;
    }
    public static void setmMarqueRecherche(String pMarqueRecherche){
        mMarqueRecherche = pMarqueRecherche;
    }
    public static void setmModeleRecherche(String pModeleRecherche){
        mModeleRecherche = pModeleRecherche;
    }

    public Image getImage(){
        return this.mImage;
    }
    public String getPrix(){
        return this.mPrix;
    }
    public String getmModele(){
        return this.mModele;
    }
    public String getMarque(){
        return this.mMarque;
    }

    public static int getCount(){
        return mOffres.size();
    }
    public String toString(){
        return mMarque + mModele;
    }


    public static ArrayList<Offre> createOffreListe() {
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
                        for (int i = 0; i <mOffres.size() ; i++){
                            mOffres.remove(i);
                        }
                        mOffres = new ArrayList<Offre>();
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray array = jsonResponse.getJSONArray("content");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            JSONObject objChild = (JSONObject) obj.get("model");
                            String nameBrand = objChild.getString("name");
                            JSONObject objChildBrand = (JSONObject) objChild.get("brand");
                            String nameModel = objChildBrand.getString("name");
                            //Log.d("Marque", "Trouve : " + nameBrand + "   Cherche : " + mMarqueRecherche + "   : Modele :" + mModeleRecherche);
                            if (nameBrand.equals(mModeleRecherche)){
                                String prix = obj.getString("price");
                                mOffres.add(new Offre(prix, nameBrand, nameModel));
                            }

                        }
                        Log.d("Nombre", Integer.toString(Offre.getCount()));
                        for (int i = 0; i < Offre.mOffres.size(); i++){
                            Log.d("Offre" + Integer.toString(i), mOffres.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return retourneOffres();
    }

    private static ArrayList<Offre> retourneOffres(){
        return mOffres;
    }

}
