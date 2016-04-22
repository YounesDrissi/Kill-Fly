package com.abdellah.pcsalon.myapplication.bleutooth;

/**
 * Created by Younes on 28/03/2016.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Younes on 28/03/2016.
 */

public class Bleutooth extends Activity {

    private Set<BluetoothDevice> mArrayAdapter;
    public String dStarted = BluetoothAdapter.ACTION_DISCOVERY_STARTED;
    public String dFinished = BluetoothAdapter.ACTION_DISCOVERY_FINISHED;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08102009c666");

    private AcceptThread mSecureAcceptThread;
    private ConnectThread mConnectThread;
    public ConnectedThread mConnectedThread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mHandler = new Handler();
        mArrayAdapter=new HashSet<BluetoothDevice>();
//      mConnectThread = new ConnectThread[10];
        mConnectThread = null;
        mSecureAcceptThread = null;
        mConnectedThread = null;

        SharedPreferences preferences = null;
        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);


        if (mBluetoothAdapter != null) {
            if (preferences.getBoolean("BLUETOOTH_AUTO", true) && (!mBluetoothAdapter.isEnabled() || mBluetoothAdapter.getScanMode()!=BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)) {
                //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(discoverableIntent, 0);//??? ignore secu?
            }
            else{
                activBt();
            }
        }
        else{
            Toast.makeText(Bleutooth.this, "Votre appareil ne supporte pas le bluetooth", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        if (requestCode == 0) {
            boolean isDiscoverable = resultCode > 0;
            if (!isDiscoverable){
                Toast.makeText(Bleutooth.this, "Vous devez activer le bluetooth pour pouvoir utiliser l'application", Toast.LENGTH_SHORT);
                //finish();
                activBt();
            }
            else{
                activBt();
            }
        }
    }

    private void activBt(){

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread();
            mSecureAcceptThread.start();
        }


        if(!mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.startDiscovery();
        }

        BroadcastReceiver discoveryMonitor = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                if(dStarted.equals(intent.getAction())) {
                    //discover start
                    Toast.makeText(Bleutooth.this, "Decouverte en cours...", Toast.LENGTH_SHORT).show();
                }
                else if(dFinished.equals(intent.getAction())) {
                    //discover finish
                    Toast.makeText(Bleutooth.this, "Decouverte terminée", Toast.LENGTH_SHORT).show();
                    connect();
                }
            }
        };
        registerReceiver(discoveryMonitor, new IntentFilter(dStarted));
        registerReceiver(discoveryMonitor, new IntentFilter(dFinished));


        // Create a BroadcastReceiver for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device);
                    Toast.makeText(Bleutooth.this, device.getName()+" "+device.getAddress(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        // Register the BroadcastReceiver
        registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND)); // Don't forget to unregister during onDestroy

    }

    private void connect(){


        if (mArrayAdapter.size() > 0) {
            // Start the thread to listen on a BluetoothServerSocket
            Iterator<BluetoothDevice> ite = mArrayAdapter.iterator();



            int i=0;
            BluetoothDevice devTmp = null;
            while(ite.hasNext()){
                try{
                    devTmp = ite.next();
                    devTmp = mBluetoothAdapter.getRemoteDevice(devTmp.getAddress());
                    mConnectThread = new ConnectThread(devTmp);
                    mConnectThread.start();
                }
                catch (Exception e){
                    Toast.makeText(Bleutooth.this, "Tentative co echoue", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final

            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
                //tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                Toast.makeText(Bleutooth.this, "erreur lors de co au serveur", Toast.LENGTH_SHORT).show();
            }
            mmSocket = tmp;
        }

        public void run() {
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            //manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("meetphone", uuid);
                //TODO tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("meetphone", uuid);
            } catch (IOException e) {
                Toast.makeText(Bleutooth.this, "thread serveur: construct fail", Toast.LENGTH_SHORT).show();
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while(true){
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Toast.makeText(Bleutooth.this, "thread serveur: fail", Toast.LENGTH_SHORT).show();
                    try {
                        socket.close();
                    } catch (IOException closeException) { }
                }
                // If a connection was accepted
                if (socket != null) {
                    //Do work to manage the connection (in a separate thread)
                    //manageConnectedSocket(socket);
                    try {
                        mConnectedThread = new ConnectedThread(socket);
                        mConnectedThread.start();
                        mmServerSocket.close();
                    } catch (IOException e) {
                        try {
                            socket.close();
                        } catch (IOException closeException) { }
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Toast.makeText(Bleutooth.this, "construct thread connected", Toast.LENGTH_SHORT).show();
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            Toast.makeText(Bleutooth.this, "connexion établi", Toast.LENGTH_SHORT).show();
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI Activity
                    //mHandler.obtainMessage(0, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main Activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main Activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}

