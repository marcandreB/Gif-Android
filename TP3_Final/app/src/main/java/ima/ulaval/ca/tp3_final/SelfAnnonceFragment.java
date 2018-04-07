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


public class SelfAnnonceFragment extends Fragment {
    private RecyclerView mRvOffres;
    private ArrayList<Offre> offres = new ArrayList<>();
    private OffreAdapter mAdapter;
    private Context mContext;


    public SelfAnnonceFragment() {
        // Required empty public constructor
    }

    public static SelfAnnonceFragment newInstance() {
        SelfAnnonceFragment fragment = new SelfAnnonceFragment();
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
        final View view = inflater.inflate(R.layout.fragment_self_annonce, container, false);
        mRvOffres = view.findViewById(R.id.rvMarques);
        OffreAdapter adap = new OffreAdapter(getContext(), offres);
        mRvOffres.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOffres.setAdapter(adap);
        fetchData(view);
        return view;
    }


    private void fetchData(View view){
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // Display the requested data on UI in main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ok", "heh");
                            mRvOffres.setLayoutManager(new LinearLayoutManager(getContext()));
                            mAdapter = new OffreAdapter(getContext(), offres);
                            mRvOffres.setAdapter(mAdapter);
                        }
                    });
                }
            }
        });
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
