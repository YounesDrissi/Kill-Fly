package com.abdellah.pcsalon.myapplication.bleutooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abdellah.pcsalon.myapplication.R;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Younes on 29/03/2016.
 */
public class Activite1 extends AppCompatActivity {


    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private Set<BluetoothDevice> pairedDevices ;
    private static int DISCOVERY_REQUEST = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivite2);
        System.out.println("In OnCreate");
        affichebluetooth();
    }

    @Override
    protected void onStart() {
        System.out.println("In OnStart");
        Log.i("", "onStart");
        if (bluetooth == null) {
            Toast.makeText(Activite1.this,
                    "No Bluetooth devices", Toast.LENGTH_LONG).show();
            Log.e("ERROR", "No Bluetooth devices");
        } else {
            Toast.makeText(Activite1.this, "Bluetooth available",
                    Toast.LENGTH_SHORT).show();
            Log.i("INFO", "Bluetooth available");
        }
        super.onStart();
    }

    void affichebluetooth()
    { System.out.println("In AfficherBl");
        if (bluetooth.isEnabled())
        {
            Toast.makeText(Activite1.this, "Bluetooth activé", Toast.LENGTH_SHORT).show();
            pairedDevices= bluetooth.getBondedDevices();

            Intent disc = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(disc, DISCOVERY_REQUEST);
        }
        else
        {
            //Pour savoir quand l'état du Bluetooth change
            String actionStatechanged=BluetoothAdapter.ACTION_STATE_CHANGED;

            String actionrequestEnable=BluetoothAdapter.ACTION_REQUEST_ENABLE;
            registerReceiver(bluetoothState, new IntentFilter(actionStatechanged));
            startActivityForResult(new Intent(actionrequestEnable), 0);
        }
    }

    // creation du broadcastreceiver pour recuperer les messages d'etat
    BroadcastReceiver bluetoothState = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("In OnReceuve BluetState");
            //Pour connaître l'état courant et l'état antérieur du Bluetooth
            String prevstateExtra= BluetoothAdapter.EXTRA_PREVIOUS_STATE;
            String stateExtra= BluetoothAdapter.EXTRA_STATE;

            int state = intent.getIntExtra(stateExtra, -1);
            int previousState = intent.getIntExtra(prevstateExtra, -1);


            switch (state){
                case (BluetoothAdapter.STATE_TURNING_ON):{
                    Toast.makeText(Activite1.this, "Bluetooth en cours d'activation", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_ON):{
                    Toast.makeText(Activite1.this, "Bluetooth activé", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(this);
                    affichebluetooth();
                    break;
                }
                case (BluetoothAdapter.STATE_TURNING_OFF):{
                    Toast.makeText(Activite1.this, "Bluetooth en cours de désactivation", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_OFF):{
                    Toast.makeText(Activite1.this, "Bluetooth désactivé", Toast.LENGTH_SHORT).show();
                    break;
                }
                default: break;
            }
        }


    };






//////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // verifier en retour si la demande d'activation du bluetooth a été validée.
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ENABLE_BLUETOOTH)
        {
            if (resultCode == RESULT_OK) {
                Toast.makeText(Activite1.this, "Activation Bluetooth Effectuée!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Activite1.this, "Activation Bluetooth Refusée!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode==DISCOVERY_REQUEST){
            Toast.makeText(Activite1.this, "Demande discovery", Toast.LENGTH_SHORT).show();
            if (!bluetooth.isDiscovering()) {
                Toast.makeText(Activite1.this, "StartDiscovery", Toast.LENGTH_SHORT).show();
                registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));

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
                Toast.makeText(Activite1.this, "péripherique detecté!", Toast.LENGTH_SHORT).show();
                BluetoothDevice remoteDevice;
                remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!bluetooth.getBondedDevices().contains(remoteDevice))
                {
                    // le périphérique n'était pas déja apparié
                    String namedevice = remoteDevice.getName();
                    String adressedevice = remoteDevice.getAddress();
                    Toast.makeText(Activite1.this, "Device detecté\n"+namedevice+"\n"+adressedevice, Toast.LENGTH_LONG).show();
                }
                else
                {
                    // périphérique déja appariée, affichage témoin
                    String namedevice = remoteDevice.getName();
                    String adressedevice = remoteDevice.getAddress();
                    Toast.makeText(Activite1.this, "Device apairé detecté\n"+namedevice+"\n"+adressedevice, Toast.LENGTH_LONG).show();
                }

            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                // fin du processu de découverte des périphériques
                if (bluetooth!=null)
                    bluetooth.cancelDiscovery();
                unregisterReceiver(this);
                Toast.makeText(Activite1.this, "Fin de la detection\n", Toast.LENGTH_SHORT).show();

                Toast.makeText(Activite1.this, "nb devices apairés: "+ pairedDevices.size(), Toast.LENGTH_SHORT).show();

                Iterator<BluetoothDevice> itdevice = pairedDevices.iterator();
                if(itdevice.hasNext())
                {
                    BluetoothDevice localdevice =itdevice.next();
                    String namedevice = localdevice.getName();
                    String adressedevice = localdevice.getAddress();
                    Toast.makeText(Activite1.this, "Device découvert\n "+namedevice+"\n"+adressedevice, Toast.LENGTH_LONG).show();
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
