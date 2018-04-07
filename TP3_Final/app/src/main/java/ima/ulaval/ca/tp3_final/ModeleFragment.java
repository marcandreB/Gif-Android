package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class ModeleFragment extends Fragment {

    private ArrayList<Modele> modeles;
    private RecyclerView rv;
    public String marque;
    private Context mContext;

    public ModeleFragment() {
        // Required empty public constructor
    }
    public static ModeleFragment newInstance() {
        ModeleFragment fragment = new ModeleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            marque = bundle.getString("marque");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_modele, container, false);
        rv = view.findViewById(R.id.rvMarques);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ModeleAdapter ada = new ModeleAdapter(getContext(), modeles);
        rv.setAdapter(ada);
        fetchData();
        return view;
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
                    modeles = new ArrayList<Modele>();
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ok", "heh");
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        ModeleAdapter adapter = new ModeleAdapter(getContext(), modeles);
                        rv.setAdapter(adapter);
                    }
                });
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
