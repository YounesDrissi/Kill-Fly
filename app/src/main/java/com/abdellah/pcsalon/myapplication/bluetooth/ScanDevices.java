package com.abdellah.pcsalon.myapplication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Younes on 25/04/2016.
 */
public class ScanDevices extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdevice);


    }
}