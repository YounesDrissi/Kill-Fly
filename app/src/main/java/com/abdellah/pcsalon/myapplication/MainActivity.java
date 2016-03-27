package com.abdellah.pcsalon.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.ExtractEditText;
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

import com.abdellah.pcsalon.myapplication.dynamicGraph.DynamicGraphActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Context contex;


    private int[] tabIcons = {
            R.mipmap.reticule,
            R.mipmap.reticule,
            R.mipmap.reticule
    };

    private Handler myHandler;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            // Code à éxécuter de façon périodique

            //Log.d(TAG, "myRunnable yes...");
            update();
            myHandler.postDelayed(this, 10);
           // changeLedNotificationColor();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*
        contex=this;

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
        myHandler.postDelayed(myRunnable,10); // on redemande toute les 500ms


    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.chrono, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.reticule_tab, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.diagramme, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(),"Chronomètre");
        adapter.addFrag(new TwoFragment(),"Kill-Fly");
        adapter.addFrag(new DynamicGraphActivity(), "Diagramme");
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



    public void update() {
        //hight,y=946;with,x=1248
        try {
          //  Log.d(TAG, "update yes...");
            ImageView v= (ImageView) findViewById(R.id.reticule_client);
            //v.setX((v.getX() + 1) % 500);
            //v.setY((v.getY() + 1) % 500);
            //v.setX();
            float diference=(((ImageView) findViewById(R.id.reticule_client)).getWidth()-26)/2;
            diference=0;
            float xR=(((ImageView) findViewById(R.id.reticule_client)).getWidth()/2)-diference;
            float yR=(((ImageView) findViewById(R.id.reticule_client)).getHeight()/2)-diference;
            float x=((ImageView) findViewById(R.id.client_cible)).getWidth()/2
                        -xR;
            float y=((ImageView) findViewById(R.id.client_cible)).getHeight()/2
                        -yR;
            v.setX(130-xR);
            v.setY(y-yR);
            //TwoFragment.relativeLayout.getChildAt(1).setY((TwoFragment.relativeLayout.getChildAt(1).getY() + 1) % 500);
            //TwoFragment.relativeLayout.getChildAt(1).setX((TwoFragment.relativeLayout.getChildAt(1).getX()+1)%500);
            ExtractEditText t=(ExtractEditText) findViewById((R.id.extractEditText));
            t.setText("x :"+v.getX()+", y :"+v.getY()+"; withe :"+((ImageView) findViewById(R.id.client_cible)).getWidth()
            +", height : "+((ImageView) findViewById(R.id.client_cible)).getHeight()
            +"; taileRX:"+(xR)+", tailleRy:"+(yR)+", dife:"+diference);
           // Log.d(TAG, "update yes...");
        }catch (Exception e){
          //  Log.d(TAG, "update no...");
            //update();
        }
    }

    public void changeLedNotificationColor(){

        if(TwoFragment.relativeLayout.getChildAt(1).getX()<100) {

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notif = new Notification();
            notif.ledARGB = Color.GREEN;
            notif.flags = Notification.FLAG_SHOW_LIGHTS;
            notif.ledOnMS = 100;
            notif.ledOffMS = 100;
            nm.notify(1, notif);
        }
        else
        {
            NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
            Notification notif = new Notification();
            notif.ledARGB = Color.BLUE;
            notif.flags = Notification.FLAG_SHOW_LIGHTS;
            notif.ledOnMS = 100;
            notif.ledOffMS = 100;
            nm.notify(1, notif);
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