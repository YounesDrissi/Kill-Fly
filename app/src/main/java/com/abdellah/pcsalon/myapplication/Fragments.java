package com.abdellah.pcsalon.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdellah.pcsalon.myapplication.bluetooth.BluetoothDemo;
import com.abdellah.pcsalon.myapplication.chrono.Chronometre;
import com.abdellah.pcsalon.myapplication.dynamicGraph.DynamicGraphActivity;
import com.abdellah.pcsalon.myapplication.dynamicGraph.Point;
import com.abdellah.pcsalon.myapplication.gestionListeSpinner.AndroidSpinnerExampleActivity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Fragments extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private BluetoothAdapter bluetoothAdapter = null;
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;

    public static Context contex;

    private TextView directionView;
    public static ImageView v;
    TextView densiteView;
    DataInputStream dataInputStream;
    OutputStream outputStream;
    private BluetoothSocket socket;

    private Handler myHandler;
    private boolean debut = true;
    private int i = 0;

    //donnees pour mettre a jour le graphe
    public double speedVent=0;
    public double directionVent=0;
    public int couleur=Color.RED;

    private Runnable myRunnable = new Runnable() {


        @Override
        public void run() {
            // Code à éxécuter de façon périodique

            //Log.d(TAG, "myRunnable yes...");
            if (debut) {
                boolean afficher = true;
                while (afficher) {
                    try {
                        initialisationCordonnees();

                        afficher = false;
                    } catch (Exception e) {
                        System.out.println("rapide");

                    }
                }
            }
            if(!debut) {
                i = i + 5;

                graphe.getLine().addNewPoints(new Point(i, (int) speedVent), (int) directionVent);
                graphe.getView().repaint();

            }else {
                debut = false;
            }

            myHandler.postDelayed(myRunnable, 5000);

        }
    };
    private boolean premierAppel=true;
    private DynamicGraphActivity graphe;
    private Thread thread;





    public void traiterBluetooth() {

        Intent intent = getIntent();
        //tester si le bluetooth est supporté par le téléphone
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            Toast.makeText(Fragments.this, "Pas de Bluetooth",
                    Toast.LENGTH_LONG).show();
        else
            System.out.println("Vous avez le bluetooth");
        //Demander l'activation du bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        } else if (intent != null && intent.getIntExtra("demo", 0) == 3) {
        } else {
            Intent intent1 = new Intent(Fragments.this, BluetoothDemo.class);
            startActivity(intent1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK) {
            Intent intent1 = new Intent(Fragments.this, BluetoothDemo.class);
            startActivity(intent1);
        } else {
            if (intent != null && intent.getIntExtra("mainActivitePassage", 0) == 1) {
                Intent intent2 = new Intent(Fragments.this, MainActivity.class);
                startActivity(intent2);
            }
            if (intent.getIntExtra("extraAjout", 0) == 2) {
                Intent intent3 = new Intent(Fragments.this, AndroidSpinnerExampleActivity.class);
                startActivity(intent3);
            }
        }
    }

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


        if (BluetoothDemo.socket != null && BluetoothDemo.socket.isConnected()) {
            try {
                this.socket = BluetoothDemo.socket;
                this.dataInputStream = new DataInputStream(this.socket.getInputStream());
                this.outputStream = this.socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myHandler = new Handler();
            Handler handler=new Handler();
            myHandler.postDelayed(myRunnable, 0); // on redemande toute les 500ms
            Handler handlerView = new Handler() {


                @Override
                public void handleMessage(Message msg) {
                    // Incrémenter la ProgressBar, on est bien dans la Thread de l'IHM
                    directionVent=msg.getData().getDouble("tr");
                    speedVent=msg.getData().getDouble("ws");
                    couleur=msg.getData().getInt("couleur");
                    try {
                        directionView.setText("Intensité :" + directionVent);
                        densiteView.setText("Direction :" + speedVent);
                        v.setX(msg.getData().getFloat("x"));
                        v.setY(msg.getData().getFloat("y"));
                        Chronometre.getCercle().color = couleur;
                        Chronometre.getCercle().invalidate();
                    }catch (Exception e){

                    }

                    //maj du graphe
                    //int temps=msg.getData().getInt("temp");



                }
            };
            UpdateAffichage updateAffichage=new UpdateAffichage(this,handlerView );
            thread=new Thread(updateAffichage);
            thread.start();

            //thread.start();
        }

        traiterBluetooth();
    }

    private void setupTabIcons() {

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.target, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.graphic, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.clock_1, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        graphe=new DynamicGraphActivity();
        adapter.addFrag(new Chronometre(), "Chronomètre");
        adapter.addFrag(new TwoFragment(), "Kill-Fly");
        adapter.addFrag(graphe, "Diagramme");
        viewPager.setAdapter(adapter);
    }

    public void majDensite(int direction) {
        System.out.println("change densite : "+direction);
        directionView = (TextView) findViewById((R.id.direction));
        directionView.setText("Direction : " + direction);
    }

    public void majIntensite(int intensite) {
        System.out.println("change intensite : "+intensite);

        densiteView = (TextView) findViewById((R.id.intensite));
        densiteView.setText("Intensité/WS : " + intensite);

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
        //initialisation de views
        v = (ImageView) findViewById(R.id.reticule_client);
         directionView = (TextView) findViewById((R.id.direction));

         densiteView = (TextView) findViewById((R.id.intensite));
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

    public TextView getDirectionView() {
        return directionView;
    }

    public ImageView getV() {
        return v;
    }

    public TextView getDensiteView() {
        return densiteView;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public DynamicGraphActivity getGraphe() {
        return graphe;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            try {
                BluetoothDemo.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

