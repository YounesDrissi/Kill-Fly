package com.abdellah.pcsalon.myapplication.bleutooth;

/**
 * Created by Younes on 28/03/2016.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.abdellah.pcsalon.myapplication.R;


public class test extends Activity {


    BluetoothAdapter bluetooth =BluetoothAdapter.getDefaultAdapter();
    private static int DISCOVERY_REQUEST = 1;
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private ArrayList<BluetoothDevice> foundDevices;

    TextView temoin;
    private void affiche(String texte){
        // petit stub pour faciliter l'affichage des données temoin.
        temoin.append("\n"+texte);
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        registerReceiver(discoveryResult,
                new IntentFilter(BluetoothDevice.ACTION_FOUND));

        temoin=(TextView) findViewById(R.id.text1);

        // test existence adapter bluetooth
        if (bluetooth==null) {
            //Toast.makeText(this, "Pas de Bluetooth", Toast.LENGTH_SHORT).show();
            affiche("Pas de Bluetooth");


        }
        else
            affichebluetooth();

    }

    private Set<BluetoothDevice> pairedDevices ;
    private Set<BluetoothDevice> discoveredDevices ;

    /* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();
    }

    private String appareiladdress="";
    private String appareilname="";

    void affichebluetooth()
    {
        String ToastText;
        if (bluetooth.isEnabled())
        {
            pairedDevices= bluetooth.getBondedDevices();
            appareiladdress=bluetooth.getAddress();
            appareilname=bluetooth.getName();
            ToastText="Bluetooth activé\n"+appareilname+" : "+appareiladdress;

            Intent disc = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(disc, DISCOVERY_REQUEST);
        }
        else
        {
            ToastText="Bluetooth désactivé";
            String actionStatechanged=BluetoothAdapter.ACTION_STATE_CHANGED;
            String actionrequestEnable=BluetoothAdapter.ACTION_REQUEST_ENABLE;
            registerReceiver(bluetoothState, new IntentFilter(actionStatechanged));
            startActivityForResult(new Intent(actionrequestEnable), 0);
        }
        affiche(ToastText);
    }

    // creation du broadcastreceiver pour recuperer les messages d'etat
    BroadcastReceiver bluetoothState = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String prevstateExtra= BluetoothAdapter.EXTRA_PREVIOUS_STATE;
            String stateExtra= BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            int previousState = intent.getIntExtra(prevstateExtra, -1);

            String tt = "";
            switch (state){
                case (BluetoothAdapter.STATE_TURNING_ON):{
                    tt="BCR Bluetooth en cours d'activation";break;
                }
                case (BluetoothAdapter.STATE_ON):{
                    tt="BCR bluetooth activé";
                    unregisterReceiver(this);
                    affichebluetooth();
                    break;
                }
                case (BluetoothAdapter.STATE_TURNING_OFF):{
                    tt="BCR bluetooth en cours de désactivation";
                    break;
                }
                case (BluetoothAdapter.STATE_OFF):{
                    tt="BCR bluetooth désactivé";
                    break;
                }
                default: break;
            }
            affiche(tt);
        }


    };
    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
    *	traitement des retours d'activation du bluetooth, et de la demande de découverte des périphériques.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // verifier en retour si la demande d'activation du bluetooth a été validée.
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ENABLE_BLUETOOTH)
        {
            if (resultCode == RESULT_OK) {

                affiche("Activation Bluetooth Effectuée!");
            }
            else
            {
                affiche("Activation Bluetooth Refusée!");
            }
        }

        if (requestCode==DISCOVERY_REQUEST){
            affiche("demande discovery");
            if (!bluetooth.isDiscovering()) {
                affiche("StartDiscovery");
                registerReceiver(discoveryResult,
                        new IntentFilter(BluetoothDevice.ACTION_FOUND));

                registerReceiver(discoveryResult,new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
                bluetooth.startDiscovery();
            }
        }
    }



    BroadcastReceiver discoveryResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                affiche("péripherique detecté!");
                BluetoothDevice remoteDevice;
                remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!bluetooth.getBondedDevices().contains(remoteDevice))
                {
                    // le périphérique n'était pas déja apparié
                    String namedevice = remoteDevice.getName();
                    String adressedevice = remoteDevice.getAddress();
                    affiche("Device detecté\n"+namedevice+"\n"+adressedevice);
                }
                else
                {
                    // périphérique déja appariée, affichage témoin
                    String namedevice = remoteDevice.getName();
                    String adressedevice = remoteDevice.getAddress();
                    affiche("Device apairé detecté\n"+namedevice+"\n"+adressedevice);
                }

            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                // fin du processu de découverte des périphériques
                if (bluetooth!=null)
                    bluetooth.cancelDiscovery();
                unregisterReceiver(this);
                affiche("Fin de la detection");

                String textf="nb devices apairés: "+ pairedDevices.size();
                affiche(textf);

                Iterator<BluetoothDevice> itdevice = pairedDevices.iterator();
                if(itdevice.hasNext())
                {
                    BluetoothDevice localdevice =itdevice.next();
                    String namedevice = localdevice.getName();
                    String adressedevice = localdevice.getAddress();

                    affiche("Device découvert\n"+namedevice+"\n"+adressedevice);
				/*
				 * 	j'ai essayé ca en direct, mais ca semble bloquer le thread principal.
				 * try {
						final  UUID uuid= UUID.fromString(appareiladdress);

						BluetoothDevice device = bluetooth.getRemoteDevice(adressedevice);
						BluetoothSocket clientSocket =
								device.createInsecureRfcommSocketToServiceRecord(uuid);
						//Toast.makeText(getApplicationContext(), "ouverture socket", Toast.LENGTH_SHORT).show();
						affiche("ouverture socket");
						clientSocket.connect();
						//Toast.makeText(getApplicationContext(), "socket ouvert", Toast.LENGTH_SHORT).show();
						affiche("socket ouvert");
						clientSocket.close();
						//Toast.makeText(getApplicationContext(), "socket fermé", Toast.LENGTH_SHORT).show();
						affiche("socket fermé");
					} catch (IOException e) {
						// TODO: handle exception
					}
		    	*/  }

            }
        }
    };

}