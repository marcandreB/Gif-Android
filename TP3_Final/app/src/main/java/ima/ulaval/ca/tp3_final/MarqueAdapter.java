package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MarqueAdapter extends RecyclerView.Adapter<MarqueAdapter.ViewHolder> {

    private List<Marque> mMarque;
    private Context mContext;

    public MarqueAdapter(Context context, List<Marque>  marques) {
        mMarque = marques;
        mContext = context;
    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public MarqueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.marque_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MarqueAdapter.ViewHolder viewHolder, int position) {
        Marque contact = mMarque.get(position);
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());

    }

    @Override
    public int getItemCount() {
        return mMarque.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public LinearLayout nameLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            nameLayout = (LinearLayout) itemView.findViewById(R.id.lol);
            nameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ModeleActivity.class);
                    intent.putExtra("marque", nameTextView.getText().toString());
                    getContext().startActivity(intent);
                }

            });
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(mContext, nameTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}