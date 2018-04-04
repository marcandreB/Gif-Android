package ima.ulaval.ca.tp3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModeleFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    public ModeleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ModeleFragment newInstance() {
        ModeleFragment fragment = new ModeleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oups", "Entre233!");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modele_list, container, false);
        Log.d("oups", "Entre23sdsd3!!!!!!!!!!!!!!!fghfhfh!");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Log.d("oups", "Entre23sdsd3fghfhfh!");
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MymodeleRecyclerViewAdapter(Modeles.ITEMS, mListener));
        }
        return view;

    }


    @Override
    public void onAttach(Context context) {
        Log.d("oups", "Entre23sdsd3!");
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Modeles.ModeleItem item);
    }
}
