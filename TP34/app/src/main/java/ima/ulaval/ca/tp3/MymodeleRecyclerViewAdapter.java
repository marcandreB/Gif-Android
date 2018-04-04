package ima.ulaval.ca.tp3;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class MymodeleRecyclerViewAdapter extends RecyclerView.Adapter<MymodeleRecyclerViewAdapter.ViewHolder> {
    private final List<Modeles.ModeleItem> mValues;
    private final ModeleFragment.OnListFragmentInteractionListener mListener;

    public MymodeleRecyclerViewAdapter(List<Modeles.ModeleItem> items, ModeleFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MymodeleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_modele, parent, false);
        Log.d("oups", "create!");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MymodeleRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ok", "hehe");
                TextView hehe = (TextView) view.findViewById(R.id.content);
                hehe.getText();
                Log.d("ok",hehe.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Modeles.ModeleItem mItem;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
