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
import android.widget.TextView;

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


public class MarqueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRvMarques;
    private ArrayList<Marque> marques = new ArrayList<>();
    private MarqueAdapter mAdapter;



    public MarqueFragment() {
        // Required empty public constructor
    }
    public static MarqueFragment newInstance() {
        MarqueFragment fragment = new MarqueFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_marque, container, false);
        mRvMarques = view.findViewById(R.id.rvMarques);
        fetchData(view);
        return view;
    }


    private void feathbruh(View view){
        for (int i = 0; i < 100; i++){
            marques.add(new Marque(Integer.toString(i),Integer.toString(i)));
        }
        mRvMarques = (RecyclerView) view.findViewById(R.id.rvMarques);
        mRvMarques.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MarqueAdapter(getActivity(), marques);
        mRvMarques.setAdapter(mAdapter);
        Log.d("nombre", Integer.toString(mRvMarques.getChildCount()));
        Log.d("nombre", Integer.toString(marques.size()));
    }
    private void fetchData(View view){
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
                        Log.d("ok", "heh");
                        mRvMarques.setLayoutManager(new LinearLayoutManager(getContext()));
                        mAdapter = new MarqueAdapter(getContext(), marques);
                        mRvMarques.setAdapter(mAdapter);
                        Log.d("nombre", Integer.toString(mRvMarques.getChildCount()));
                        Log.d("nombre", Integer.toString(marques.size()));
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
