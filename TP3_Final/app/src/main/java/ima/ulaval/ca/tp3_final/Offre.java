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
    private String mID;
    private String mModele;

    public Offre(String pPrix, String pMarque, String pModele, String ID){
        this.mPrix = pPrix;
        this.mMarque = pMarque;
        this.mModele = pModele;
        this.mID = ID;
    }
    public static ArrayList<Offre> mOffres = new ArrayList<>();
    public void setName(Image pImage){
        this.mImage = pImage;
    }
    public void setMarque(String pMarque){
        this.mMarque = pMarque;
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
    public String getID(){
        return this.mID;
    }

    @Override
    public String toString(){
        return mMarque + mModele;
    }


}
