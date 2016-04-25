package com.abdellah.pcsalon.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdellah.pcsalon.myapplication.chrono.Chronometre;
import com.abdellah.pcsalon.myapplication.dynamicGraph.DynamicGraphActivity;


import java.util.ArrayList;
import java.util.List;

public class Fragments extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Context contex;


    private Handler myHandler;
    private boolean debut = true;
    private int i = 0;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            // Code à éxécuter de façon périodique

            //Log.d(TAG, "myRunnable yes...");
            if (debut) {
                initialisationCordonnees();
                debut = false;
            }
            i++;
            i = (i % 12);
            System.out.println("i:" + i);
            update(i, i, i);
            myHandler.postDelayed(this, 1000);

            // changeLedNotificationColor();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contex = this;

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        myHandler = new Handler();
        myHandler.postDelayed(myRunnable, 1); // on redemande toute les 500ms

    }

    private void setupTabIcons() {

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.reticule_tab, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.diagramme, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabThree);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.chrono, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TwoFragment(), "Kill-Fly");
        adapter.addFrag(new DynamicGraphActivity(), "Diagramme");
        adapter.addFrag(new Chronometre(), "Chronomètre");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void initialisationCordonnees() {
        //float diference=(findViewById(R.id.reticule_client).getWidth()-26)/2;
        float diference = 0;
        float xR = (findViewById(R.id.reticule_client).getWidth() / 2);
        float yR = (findViewById(R.id.reticule_client).getHeight() / 2);
        TwoFragment.setxZero(findViewById(R.id.cible22Fin).getWidth() / 2
                - xR - (1 / 2));
        TwoFragment.setyZero(findViewById(R.id.cible22Fin).getHeight() / 2
                - yR + 3);
    }

    public void update(int direction, float distance, int intensite) {
        //hight,y=946;with,x=1248k
        distance = TwoFragment.getHuite();
        try {
            //  Log.d(TAG, "update yes...");
            ImageView v = (ImageView) findViewById(R.id.reticule_client);
            Chronometre.getCercle().repaint(Color.BLACK);
            // float devise27=(TwoFragment.getxZero()/3);
            switch (direction) {
                case 0:
                    v.setX(TwoFragment.getxZero());
                    v.setY(TwoFragment.getyZero() - distance);
                    break;
                case 1:
                    v.setX((float) (TwoFragment.getxZero() + ((distance / 2.15))));
                    v.setY((float) (TwoFragment.getyZero() - ((distance / 1.11))));
                    break;
                case 2:
                    v.setX((float) (TwoFragment.getxZero() + ((distance / 1.11))));
                    v.setY((float) (TwoFragment.getyZero() - ((distance / 2.15))));
                    break;
                case 3:
                    v.setX(TwoFragment.getxZero() + distance);
                    v.setY(TwoFragment.getyZero());
                    break;
                case 4:
                    v.setX((float) (TwoFragment.getxZero() + ((distance / 1.11))));
                    v.setY((float) (TwoFragment.getyZero() + ((distance / 2.15))));
                    break;
                case 5:
                    v.setX((float) (TwoFragment.getxZero() + ((distance / 2.15))));
                    v.setY((float) (TwoFragment.getyZero() + ((distance / 1.11))));
                    break;
                case 6:
                    v.setX(TwoFragment.getxZero());
                    v.setY(TwoFragment.getyZero() + distance);
                    break;
                case 7:
                    v.setX((float) (TwoFragment.getxZero() - ((distance / 2.15))));
                    v.setY((float) (TwoFragment.getyZero() + ((distance / 1.11))));
                    break;
                case 8:
                    v.setX((float) (TwoFragment.getxZero() - ((distance / 1.11))));
                    v.setY((float) (TwoFragment.getyZero() + ((distance / 2.15))));
                    break;
                case 9:
                    v.setX(TwoFragment.getxZero() - distance);
                    v.setY(TwoFragment.getyZero());
                    break;
                case 10:
                    v.setX((float) (TwoFragment.getxZero() - ((distance / 1.11))));
                    v.setY((float) (TwoFragment.getyZero() - ((distance / 2.15))));
                    break;
                case 11:
                    v.setX((float) (TwoFragment.getxZero() - ((distance / 2.15))));
                    v.setY((float) (TwoFragment.getyZero() - ((distance / 1.11))));
                    break;


            }
            TextView directionView = (TextView) findViewById((R.id.direction));
            directionView.setText("Direction : " + direction);

            TextView densiteView = (TextView) findViewById((R.id.intensite));
            densiteView.setText("Intensité : " + intensite);
           /* t.setText("x :" + v.getX() + ", y :" + v.getY() + "; withe :" + ((ImageView) findViewById(R.id.cible22Fin)).getWidth()
                    + ", height : " + ((ImageView) findViewById(R.id.cible22Fin)).getHeight()
                    + "i: "+distance);
           // Log.d(TAG, "update yes...");*/
        } catch (Exception e) {
            //  Log.d(TAG, "update no...");
            //update();
        }
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "pauseeeed...");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Start...");
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
