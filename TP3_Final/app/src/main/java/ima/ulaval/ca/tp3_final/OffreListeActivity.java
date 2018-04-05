package ima.ulaval.ca.tp3_final;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class OffreListeActivity extends AppCompatActivity {

    private ArrayList<Offre> offres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offre_liste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Offre.setmMarqueRecherche(getIntent().getExtras().getString("Marque"));
        Offre.setmModeleRecherche(getIntent().getExtras().getString("Modele"));
        RecyclerView rvMarques = (RecyclerView) findViewById(R.id.rvMarques);
        rvMarques.setLayoutManager(new LinearLayoutManager(this));
        offres = Offre.createOffreListe();
        synchronized (this) {
            try {
                wait(2000);
                Log.d("Compte dans actibity :" , Modele.getCount());
                rvMarques.setLayoutManager(new LinearLayoutManager(this));
                OffreAdapter adapter = new OffreAdapter(this, offres);
                rvMarques.setAdapter(adapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}