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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Meh on 2018-04-04.
 */

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.ViewHolder> {

    private List<Offre> mOffres;
    private Context mContext;

    public OffreAdapter(Context context, List<Offre> offres) {
        mOffres = offres;
        mContext = context;
    }
    public OffreAdapter(List<Offre>  offres) {
        mOffres = offres;
    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public OffreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.offre_item, parent, false);
        OffreAdapter.ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OffreAdapter.ViewHolder viewHolder, int position) {
        Offre contact = mOffres.get(position);

        if (position % 2 ==0){
            viewHolder.nameLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        TextView textView = viewHolder.marqueTextView;
        textView.setText(contact.getMarque());

        textView = viewHolder.modelTextView;
        textView.setText(contact.getmModele());

        textView = viewHolder.prixTextView;
        textView.setText(contact.getPrix());

        textView = viewHolder.idTextView;
        textView.setText(String.valueOf(contact.getID()));

    }

    @Override
    public int getItemCount() {
        return mOffres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView marqueTextView;
        public TextView modelTextView;
        public TextView idTextView;
        public TextView prixTextView;
        public LinearLayout nameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            marqueTextView = (TextView) itemView.findViewById(R.id.marque_name);
            modelTextView = (TextView) itemView.findViewById(R.id.modele_name);
            prixTextView = (TextView) itemView.findViewById(R.id.prix_name);
            idTextView = (TextView) itemView.findViewById(R.id.id_name);
            nameLayout = (LinearLayout) itemView.findViewById(R.id.lol);
            nameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,DescriptionActivity.class);
                    Log.d("textview", idTextView.getText().toString() );
                    intent.putExtra("ID", idTextView.getText());
                    mContext.startActivity(intent);
                }

            });
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(mContext, marqueTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
