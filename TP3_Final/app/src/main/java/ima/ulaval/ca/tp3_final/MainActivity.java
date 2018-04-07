package ima.ulaval.ca.tp3_final;

import android.app.TabActivity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Marque> marques;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    private MarqueAdapter adap;
    public ViewPager getViewPager(){
        return mViewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Préparation du contenu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TP3 Marc-Andre Brunelle-Langevin");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("posituib",  Integer.toString(position));
            if (position ==0){

                return MarqueFragment.newInstance();

            }
           if (position == 1){

               return VendreFragment.newInstance();

           }
            if (position == 2){
                return SelfAnnonceFragment.newInstance();

            }
           Log.d("Spas suppose", "god stop");
            return SelfAnnonceFragment.newInstance();

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
