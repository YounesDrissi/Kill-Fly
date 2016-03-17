package com.abdellah.pcsalon.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.abdellah.pcsalon.myapplication.classPrincipal.Group;
import com.abdellah.pcsalon.myapplication.classPrincipal.MyExpandableListAdapter;

import android.util.SparseArray;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);

        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
    }/*
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

        try {
          //  Log.d(TAG, "update yes...");

            TwoFragment.relativeLayout.getChildAt(1).setY((TwoFragment.relativeLayout.getChildAt(1).getY()+1)%500 );
            TwoFragment.relativeLayout.getChildAt(1).setX((TwoFragment.relativeLayout.getChildAt(1).getX()+1)%500);
           // Log.d(TAG, "update yes...");
        }catch (Exception e){
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
}*/
}