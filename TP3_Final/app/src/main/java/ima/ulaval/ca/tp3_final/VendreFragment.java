package ima.ulaval.ca.tp3_final;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class VendreFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ArrayList<Marque> marques = new ArrayList<>();
    private Spinner mSpinnerMarque;
    private String mMarque_ID = "1";
    private HashMap<Integer, String> mSpinnerMapMarque = new HashMap<>();

    private ArrayList<Modele> modeles = new ArrayList<>();
    private Spinner mSpinnerModele;
    private String mModele_ID = "1";
    private HashMap<Integer, String> mSpinnerModeleMap= new HashMap<>();

    private Spinner mSpinnerTransmission;
    private EditText mPrix;
    private EditText mAnnee;
    private EditText mDescription;
    private Button mButtonEnvoyer;
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

    public void fetchData0Marque(){
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
                        Log.d("entre","hehdfdsfsde");
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


                    // Display the requested data on UI in main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] spinnerArray = new String[marques.size()];
                            mSpinnerMapMarque = new HashMap<>();
                            for (int i = 0 ; i < marques.size() ; i++) {
                                Marque currentMarque = marques.get(i);
                                mSpinnerMapMarque.put(i, currentMarque.getID());
                                spinnerArray[i] = currentMarque.getName();
                            }
                            Log.d("entre","YOURE SUPPOSE TO HELP");
                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            mSpinnerMarque.setAdapter(adapter);
                            Log.d("marque size", Integer.toString(marques.size()));
                        }
                    });
                }

        });
    }

    public void fetchDataModele(){
        OkHttpClient client = new OkHttpClient();
        String id;
        modeles = new ArrayList<Modele>();
        if (mMarque_ID == null)
            id = "1";
        else
            id = mMarque_ID;
        Request request = new Request.Builder()
                .url("http://159.203.34.137:80/api/v1/brands/" + id + "/models/" )
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
                        Log.d("entre","hehdfdsfsde");
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




                // Display the requested data on UI in main thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] spinnerArray = new String[modeles.size()];
                        mSpinnerModeleMap = new HashMap<>();
                        for (int i = 0 ; i < modeles.size() ; i++) {
                            Modele currentModele = modeles.get(i);
                            mSpinnerModeleMap.put(i, currentModele.getID());
                            spinnerArray[i] = currentModele.getName();
                        }
                        Log.d("entre","YOURE SUPPOSE TO HELP");
                        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        mSpinnerModele.setAdapter(adapter);
                        Log.d("modelee size", mSpinnerModeleMap.get(mSpinnerModele.getSelectedItemPosition()));


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
                // your code here
            }

        });
        mSpinnerModele.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mModele_ID = mSpinnerModeleMap.get(mSpinnerModele.getSelectedItemPosition());
                Log.d("MAsdffffffffffffffffffffffffffffffffffJ", "MAJ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                mModele_ID = "1";
            }

        });
        fetchData0Marque();

        String[] arrayTransmission = new String[] {
                "MA", "AT", "RB"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arrayTransmission);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinnerTransmission.setAdapter(adapter);
        mAnnee = view.findViewById(R.id.txtAnnee);
        mDescription = view.findViewById(R.id.txtDescription);
        mPrix = view.findViewById(R.id.txtPrix);
        mButtonEnvoyer = view.findViewById(R.id.btnEnvoyer);
        mButtonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Log.d("success", "yass");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Votre annonce est bien affichee!",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                });
            }

        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
