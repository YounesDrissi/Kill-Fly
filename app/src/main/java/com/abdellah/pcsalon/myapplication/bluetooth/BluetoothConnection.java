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
import java.io.InputStreamReader;
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
    private final BluetoothAdapter adapter;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private Context context;
    private String model;
    private int state;

    private class ConnectThread extends Thread {
        private final BluetoothDevice device;
        private final BluetoothSocket socket;

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(BluetoothConnection.uuid);
            } catch (IOException e) {
                Log.e(BluetoothConnection.TAG, "Socket create failed", e);
            }
            this.socket = tmp;
        }

        public void run() {
            Log.i(BluetoothConnection.TAG, "BEGIN ConnectThread");
            try {
                this.socket.connect();
                synchronized (BluetoothConnection.this) {
                    BluetoothConnection.this.connectThread = null;
                }
                BluetoothConnection.this.connected(this.socket, this.device);
            } catch (IOException e) {
                try {
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

        public ConnectedThread(BluetoothSocket socket) throws IOException {
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
            d = new BufferedReader(new InputStreamReader(inStream, "ISO-8859-1"));

            StringBuilder sb = new StringBuilder();
            String line = "";
            System.out.println("avant while 1");
            while (d.ready() && (line = d.readLine()) != null) {
                sb.append(line + "\r\n");
                System.out.println("testttt");
            }
            System.out.println("apres while 1");
            String result = sb.toString();
        }


        public void run() {
            Log.i(BluetoothConnection.TAG, "BEGIN ConnectedThread");
            byte[] buffer = new byte[1024];

            while (true) {
                Log.i(BluetoothConnection.TAG, "Reading...");
                try {
                    String[] results = new String[BluetoothConnection.STATE_CONNECTED];
                    Log.i(BluetoothConnection.TAG, "Received:");
                    this.datIn.readUTF();
                    System.out.println("UTF " + this.datIn.readUTF());
                    System.out.println((this.d.readLine()).length());
                    System.out.println("jjj");
                    d.readLine();
                    System.out.println("datIn " + datIn);
                    this.datIn.read(buffer);
                    System.out.println("buffer ");
                    System.out.println("datin2 " + buffer);

                    System.out.println("kkkk1");
                    results[BluetoothConnection.STATE_NONE] = this.d.readLine();
                    System.out.println("mlmm");
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_NONE]);
                    results[BluetoothConnection.STATE_LISTEN] = this.d.readLine();
                    System.out.println("1111");
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_LISTEN]);
                    results[BluetoothConnection.STATE_CONNECTING] = this.d.readLine();
                    System.out.println("llll55");
                    Log.i(BluetoothConnection.TAG, results[BluetoothConnection.STATE_CONNECTING]);
                    Bundle data = BluetoothConnection.this.parseData(results);
                    System.out.println("kljjlj");
                    System.out.println(",k,k,k,k" + data.toString());
                    //Message m = BluetoothConnection.this.handler.obtainMessage();
                    //m.setData(data);
                    // m.what = BluetoothConnection.STATE_CONNECTED;
                    // BluetoothConnection.this.handler.sendMessage(m);
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
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    }

    public BluetoothConnection(Context context) {
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.state = STATE_NONE;
        this.context = context;
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
            this.connectThread = new ConnectThread(device);
            this.connectThread.start();
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
        this.connectedThread.start();
        //this.handler.obtainMessage(STATE_CONNECTING).sendToTarget();
        setState(STATE_CONNECTED);
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
        data.putBoolean("hasAL", false);
        data.putBoolean("hasBP", false);
        for (int i = STATE_NONE; i < splitHeaders.length; i += STATE_LISTEN) {
            String h = splitHeaders[i];
            if (h.equals("WS") || h.equals("AV")) {
                data.putString("windunit", splitUnits[i]);
                data.putDouble("ws", Double.parseDouble(splitData[i]));
            }
            if (h.equals("TP")) {
                data.putString("tempunit", splitUnits[i]);
                data.putDouble("tp", Double.parseDouble(splitData[i]));
            }
            if (h.equals("BP")) {
                data.putString("pressunit", splitUnits[i]);
                data.putDouble("bp", Double.parseDouble(splitData[i]));
                data.putBoolean("hasBP", f10D);
            }
            if (h.equals("AL")) {
                data.putString("altunit", splitUnits[i]);
                data.putDouble("al", Double.parseDouble(splitData[i]));
                data.putBoolean("hasAL", f10D);
            }
            if (h.equals("RH")) {
                data.putDouble("rh", Double.parseDouble(splitData[i]));
            }
        }
        return data;
    }
}
