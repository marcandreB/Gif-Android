package ima.ulaval.ca.tp3_final;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.*;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;
import static android.graphics.BitmapFactory.decodeFile;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class VendreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Marque> marques = new ArrayList<>();
    private Spinner mSpinnerMarque;
    private String mMarque_ID = "1";
    private HashMap<Integer, String> mSpinnerMapMarque = new HashMap<>();

    private ArrayList<Modele> modeles = new ArrayList<>();
    private Spinner mSpinnerModele;
    private String mModele_ID = "1";
    private HashMap<Integer, String> mSpinnerModeleMap = new HashMap<>();

    private Spinner mSpinnerTransmission;
    private EditText mPrix;
    private EditText mAnnee;
    private Button mButtonEnvoyer;

    private TextView tvMarque;
    private TextView tvModele;
    private TextView tvPrix;
    private TextView tvTransmission;
    private TextView tvAnnee;
    @SuppressLint("UseSparseArrays")
    Activity mActivity;
    public VendreFragment() {
    }

    public static VendreFragment newInstance() {
        VendreFragment fragment = new VendreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void fetchData0Marque() {
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
                            Marque nouvelleMarque = new Marque(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("id"));
                            marques.add(nouvelleMarque);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] spinnerArray = new String[marques.size()];
                        mSpinnerMapMarque = new HashMap<>();
                        for (int i = 0; i < marques.size(); i++) {
                            Marque currentMarque = marques.get(i);
                            mSpinnerMapMarque.put(i, currentMarque.getID());
                            spinnerArray[i] = currentMarque.getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        mSpinnerMarque.setAdapter(adapter);
                    }
                });
            }

        });
    }

    public void fetchDataModele() {
        OkHttpClient client = new OkHttpClient();
        String id;
        modeles = new ArrayList<Modele>();
        if (mMarque_ID == null)
            id = "1";
        else
            id = mMarque_ID;
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/brands/" + id + "/models/")
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
                            Modele nouveauModele = new Modele(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("id"));
                            modeles.add(nouveauModele);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] spinnerArray = new String[modeles.size()];
                        mSpinnerModeleMap = new HashMap<>();
                        for (int i = 0; i < modeles.size(); i++) {
                            Modele currentModele = modeles.get(i);
                            mSpinnerModeleMap.put(i, currentModele.getID());
                            spinnerArray[i] = currentModele.getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        mSpinnerModele.setAdapter(adapter);


                    }
                });
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_vendre, container, false);
        mSpinnerMarque = view.findViewById(R.id.spinnerMarque);
        tvMarque = view.findViewById(R.id.TVMarque);
        tvModele = view.findViewById(R.id.TVModele);
        tvPrix = view.findViewById(R.id.TVPrix);
        tvTransmission = view.findViewById(R.id.TVMarque);
        tvAnnee = view.findViewById(R.id.TVAnnee);
        mSpinnerModele = view.findViewById(R.id.spinnerModele);
        mSpinnerTransmission = view.findViewById(R.id.spinnerTransmission);
        mSpinnerMarque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mMarque_ID = mSpinnerMapMarque.get(mSpinnerMarque.getSelectedItemPosition());
                fetchDataModele();
                Log.d("MAJ", "MAJ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        mSpinnerModele.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mModele_ID = mSpinnerModeleMap.get(mSpinnerModele.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                mModele_ID = "1";
            }

        });
        fetchData0Marque();

        String[] arrayTransmission = new String[]{
                "MA", "AT", "RB"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arrayTransmission);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinnerTransmission.setAdapter(adapter);
        mAnnee = view.findViewById(R.id.txtAnnee);
        mPrix = view.findViewById(R.id.txtPrix);
        mButtonEnvoyer = view.findViewById(R.id.btnEnvoyer);
        mButtonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valide = true;
                if (mSpinnerMarque.getSelectedItem() == null) {
                    tvMarque.setTextColor(getResources().getColor(R.color.colorRouge1));
                    valide = false;
                    Toast.makeText(getActivity(), "Veuillez choisir une marque!",
                            Toast.LENGTH_LONG).show();
                } else
                    tvMarque.setTextColor(Color.BLACK);
                if (mSpinnerModele.getSelectedItem() == null) {
                    valide = false;
                    tvMarque.setTextColor(getResources().getColor(R.color.colorRouge1));
                    Toast.makeText(getActivity(), "Veuillez choisir un modele!!",
                            Toast.LENGTH_LONG).show();
                } else
                    tvMarque.setTextColor(Color.BLACK);
                if (mPrix.getText().toString().trim().length() < 1) {
                    valide = false;
                    tvPrix.setTextColor(getResources().getColor(R.color.colorRouge1));
                    Toast.makeText(getActivity(), "Veuillez inscrire un prix!!",
                            Toast.LENGTH_LONG).show();
                } else
                    tvPrix.setTextColor(Color.BLACK);
                if (mSpinnerTransmission.getSelectedItem() == null) {
                    valide = false;
                    tvTransmission.setTextColor(getResources().getColor(R.color.colorRouge1));
                    Toast.makeText(getActivity(), "Veuillez choisir une transmission plz!!",
                            Toast.LENGTH_LONG).show();
                } else
                    tvTransmission.setTextColor(Color.BLACK);
                if (mAnnee.getText().toString().trim().length() < 1) {
                    valide = false;
                    tvAnnee.setTextColor(getResources().getColor(R.color.colorRouge1));
                    Toast.makeText(getActivity(), "Veuillez inscrire une annee!!",
                            Toast.LENGTH_LONG).show();
                } else
                    tvAnnee.setTextColor(Color.BLACK);
                if (!valide)
                    return;
                OkHttpClient client = new OkHttpClient();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("transmission", mSpinnerTransmission.getSelectedItem().toString())
                        .addFormDataPart("price", mPrix.getText().toString())
                        .addFormDataPart("year", mAnnee.getText().toString())
                        .addFormDataPart("offer", "true")
                        .addFormDataPart("seller", "111120022")
                        .addFormDataPart("model", mSpinnerModeleMap.get(mSpinnerModele.getSelectedItemPosition()))
                        .build();
                Request request = new Request.Builder()
                        .url("http://159.203.34.137:80/api/v1/offers/")
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Votre annonce est bien affichee!",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), DescriptionActivity.class);
                                    intent.putExtra("ID", "nouveau");
                                    getContext().startActivity(intent);

                                }
                            });
                        }
                    }

                });
            }

        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mActivity=(Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
