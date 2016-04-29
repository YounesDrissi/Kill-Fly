package com.abdellah.pcsalon.myapplication.bluetooth;

/**
 * Created by Younes on 25/04/2016.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
/**
 * Created by Younes on 19/04/2016.
 */
@SuppressWarnings("ALL")
public class BluetoothConnection {
    private static final boolean f10D = true;
    public static final int MESSAGE_CONNECTED = 2;
    public static final int MESSAGE_READ = 3;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private static final String TAG = "BTComm";
    private static final UUID uuid;
    private static boolean connecter=false;
    private final BluetoothAdapter adapter;
    private final Handler handler;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private Context context;
    private String model;
    private int state;
    public static BluetoothSocket socket=null;



    private class ConnectThread extends Thread {
        private final BluetoothDevice device;
        private final BluetoothSocket socket;
        private final Handler handler;

        public ConnectThread(BluetoothDevice device,Handler handler) {
            this.device = device;
            this.handler=handler;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(BluetoothConnection.uuid);
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "Socket create failed", e);
            }
            this.socket = tmp;
        }
///////////


        public BluetoothSocket getSocket() {
            return socket;
        }

        ///////////////////////
        public void run() {
            Log.i(BluetoothConnection.TAG, "BEGIN ConnectThread");
            try {
                this.socket.connect();
                System.out.println(this.socket.isConnected());
                synchronized (BluetoothConnection.this) {
                    BluetoothConnection.this.connectThread = null;
                }
                BluetoothConnection.this.connected(this.socket, this.device);
            } catch (IOException e) {
                try {
                    Bundle messageBundle=new Bundle();
                    Message myMessage;
                    myMessage=this.handler.obtainMessage();
                    messageBundle.putBoolean("connecte", false);
                    myMessage.setData(messageBundle);
                    this.handler.sendMessage(myMessage);

                    this.socket.close();
                } catch (IOException e2) {
                    Log.e(BluetoothConnection.TAG, "unable to close() socket during connection failure", e2);
                }
            }
        }

        public void cancel() {
            try {
                this.socket.close();
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final DataInputStream datIn;
        private final InputStream inStream;
        private final OutputStream outStream;
        private final BluetoothSocket socket;
        private BufferedReader d;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(BluetoothConnection.TAG, "create ConnectedThread");
            this.socket = socket;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "temp sockets not created", e);
            }
            this.inStream = tmpIn;
            this.outStream = tmpOut;
            this.datIn = new DataInputStream(this.inStream);
        }


        public void run() {
            Log.i(BluetoothConnection.TAG, "BEGIN ConnectedThread");
            try {
                outStream.write("S\r".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                Log.i(BluetoothConnection.TAG, "Reading...");
                try {
                    String[] results = new String[BluetoothConnection.STATE_CONNECTED];
                    Log.i(BluetoothConnection.TAG, "Recieved:");
                    this.datIn.readLine();
                    this.datIn.readLine();
                    results[BluetoothConnection.STATE_NONE] = this.datIn.readLine();
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_NONE]);
                    results[BluetoothConnection.STATE_LISTEN] = this.datIn.readLine();
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_LISTEN]);
                    results[BluetoothConnection.STATE_CONNECTING] = this.datIn.readLine();
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_CONNECTING]);
                    Bundle data = BluetoothConnection.this.parseData(results);

                    System.out.println(data.get("tr"));
                    System.out.println(data.get("ws"));

                    System.out.println(data);
                } catch (IOException e) {
                    Log.e(BluetoothConnection.TAG, "disconnected", e);
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.outStream.write(buffer);
                Log.i(BluetoothConnection.TAG, "Sending: " + new String(buffer));
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                this.socket.close();
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "close() of connect socket failed", e);
            }
        }
    }

    static {
        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    }

    public BluetoothConnection(Context context,Handler handler) {
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.state = STATE_NONE;
        this.context = context;
        this.handler=handler;
    }

    public int checkBluetooth() {
        if (this.adapter == null) {
            return -1;
        }
        return this.adapter.isEnabled() ? STATE_LISTEN : STATE_NONE;
    }

    public BluetoothDevice getBTDevice() {
        Set<BluetoothDevice> pairedDevices = this.adapter.getBondedDevices();
        BluetoothDevice foundDevice = null;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().matches("K4\\d\\d\\d-\\d\\d\\d\\d\\d\\d")) {
                    this.model = device.getName().substring(STATE_NONE, 4);
                    foundDevice = device;
                    Log.d(TAG, "Found " + device.getName());
                    Log.d(TAG, "Model " + this.model);
                    break;
                }
            }
            if (foundDevice != null) {
                return foundDevice;
            }
            Log.d(TAG, "No paired Kestrel device was found");
            Toast.makeText(this.context, "ERROR: No paired Kestrel device was found, please pair a Kestrel device to continue", STATE_LISTEN).show();
            return null;
        }
        Log.d(TAG, "No paired Kestrel device was found-No devices paired");
        Toast.makeText(this.context, "ERROR: No paired Kestrel device was found, please pair a Kestrel device to continue", STATE_LISTEN).show();
        return null;
    }

    private synchronized void setState(int state) {
        Log.d(TAG, "setState() " + this.state + " -> " + state);
        this.state = state;
    }

    public synchronized void connect(BluetoothDevice device) {
        if (device != null) {
            Log.d(TAG, "connect to: " + device.getName());
            if (this.connectThread != null) {
                this.connectThread.cancel();
                this.connectThread = null;
            }
            if (this.connectedThread != null) {
                this.connectedThread.cancel();
                this.connectedThread = null;
            }
            this.connectThread = new ConnectThread(device,this.handler);

            this.connectThread.start();
            this.socket=this.connectThread.getSocket();
        }
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) throws IOException {
        Log.d(TAG, "connected to: " + device.getName());
        if (this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
        this.connectedThread = new ConnectedThread(socket);
        //this.connectedThread.start();
        //this.handler.obtainMessage(STATE_CONNECTING).sendToTarget();
        setState(STATE_CONNECTED);
        //
        Bundle messageBundle=new Bundle();
        Message myMessage;
        myMessage=this.handler.obtainMessage();
        messageBundle.putBoolean("connecte", true);
        myMessage.setData(messageBundle);
        this.handler.sendMessage(myMessage);
    }

    public synchronized void stop() {
        Log.d(TAG, "stop");
        if (this.connectThread != null) {
            this.connectThread.cancel();
            this.connectThread = null;
        }
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
            setState(STATE_NONE);
        }
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.state != STATE_CONNECTED) {
                return;
            }
            ConnectedThread r = this.connectedThread;
            r.write(out);
        }
    }

    private Bundle parseData(String[] dataStrings) {
        Bundle data = new Bundle();
        dataStrings[STATE_NONE] = dataStrings[STATE_NONE].replace("> ", "");
        String[] splitHeaders = dataStrings[STATE_NONE].split(",");
        String[] splitUnits = dataStrings[STATE_LISTEN].split(",");
        String[] splitData = dataStrings[STATE_CONNECTING].split(",");

        for (int i = STATE_NONE; i < splitHeaders.length; i += STATE_LISTEN) {
            String h = splitHeaders[i];
            if (h.equals("WS") || h.equals("AV")) {
                //  data.putString("windunit", splitUnits[i]);
                data.putDouble("ws", Double.parseDouble(splitData[i]));
            }
            if (h.equals("TR")) {
                // data.putString("directionWindUnit", splitUnits[i]);
                data.putDouble("tr", Double.parseDouble(splitData[i]));
            }
        }
        return data;
    }
}

