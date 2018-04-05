package ima.ulaval.ca.tp3_final;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ModeleActivity extends AppCompatActivity {

    private ArrayList<Modele> modeles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modele);
        /*
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
*/
        Modele.setMarque(getIntent().getExtras().getString("Marque"));
        RecyclerView rvMarques = (RecyclerView) findViewById(R.id.rvMarques);
        rvMarques.setLayoutManager(new LinearLayoutManager(this));
        modeles = Modele.createModeleList( rvMarques, this);
        synchronized (this) {
            try {
                wait(500);
                Log.d("Compte dans actibity :" , Modele.getCount());
                rvMarques.setLayoutManager(new LinearLayoutManager(this));
                ModeleAdapter adapter = new ModeleAdapter(this, modeles);
                rvMarques.setAdapter(adapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
