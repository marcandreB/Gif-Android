package ima.ulaval.ca.tp3_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class VendreFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ArrayList<Marque> marques;
    private Spinner mSpinnerMarque;
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

    public void fetchData(){
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


                    // Display the requested data on UI in main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] spinnerArray = new String[marques.size()];
                            @SuppressLint("UseSparseArrays") HashMap<Integer, String> spinnerMap = new HashMap<>();
                            for (int i = 0 ; i < marques.size() ; i++) {
                                Marque currentMarque = marques.get(i);
                                spinnerMap.put(i, currentMarque.getID());
                                spinnerArray[i] = currentMarque.getName();
                            }
                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerMarque.setAdapter(adapter);
                        }
                    });
                }

        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fetchData();
        Log.d("passe", "ici");
        return inflater.inflate(R.layout.fragment_vendre, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
