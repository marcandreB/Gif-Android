package ima.ulaval.ca.tp3_final;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Meh on 2018-04-04.
 */

public class ModeleAdapter extends RecyclerView.Adapter<ModeleAdapter.ViewHolder> {

    private List<Modele> mModele;
    private Context mContext;

    public ModeleAdapter(Context context, List<Modele>  modeles) {
        mModele = modeles;
        mContext = context;
        Log.d("heh", "sssssdggju");
    }
    public ModeleAdapter(List<Modele>  modeles) {
        mModele = modeles;
        Log.d("heh", "sssssdggju");
    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public ModeleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.modele_item, parent, false);
        ModeleAdapter.ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ModeleAdapter.ViewHolder viewHolder, int position) {
        Modele contact = mModele.get(position);

        if (position % 2 ==0){
            viewHolder.nameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());

    }

    @Override
    public int getItemCount() {
        return mModele.size();
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
                    Intent intent = new Intent(mContext,OffreListeActivity.class);
                    intent.putExtra("Marque", Modele.getMarque());
                    intent.putExtra("Modele", nameTextView.getText());
                    mContext.startActivity(intent);
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