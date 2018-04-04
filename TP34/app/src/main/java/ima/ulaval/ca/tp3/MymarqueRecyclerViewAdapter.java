package ima.ulaval.ca.tp3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ima.ulaval.ca.tp3.MarqueFragment.OnListFragmentInteractionListener;
import java.util.List;



public class MymarqueRecyclerViewAdapter extends RecyclerView.Adapter<MymarqueRecyclerViewAdapter.ViewHolder> {

    private final List<Marques.MarqueItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MymarqueRecyclerViewAdapter(List<Marques.MarqueItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_marque, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
                OpenModele(hehe.getText().toString());
            }
        });
    }

    private void OpenModele(String marque){
        Intent intent = new Intent(context,ModeleActivity.class);
        intent.putExtra("Marque", marque);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Marques.MarqueItem mItem;

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
