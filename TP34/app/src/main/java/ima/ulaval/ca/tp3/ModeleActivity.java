package ima.ulaval.ca.tp3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class ModeleActivity extends AppCompatActivity {

    private static String marque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modele);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        marque =  getIntent().getExtras().getString("Marque");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.d("oups", "avant!");
        //ModeleFragment frag = ModeleFragment.newInstance();
        Log.d("oups", "Apres!");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ModeleFragment fragment = new ModeleFragment();
        fragmentTransaction.add(R.id.containerModele, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        getSupportFragmentManager().executePendingTransactions();
        Log.d("oups", "Fragment crée!");
    }

    public static String getMarque(){
        return marque;
    }

}
