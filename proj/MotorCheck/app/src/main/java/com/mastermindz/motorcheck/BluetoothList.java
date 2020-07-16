package com.mastermindz.motorcheck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothList extends AppCompatActivity  {
    private String TAG = "request message",x,y;
    private ListView lstvw;
    private ArrayAdapter aAdapter;
    ArrayList list = new ArrayList();
    ArrayList hwrlst = new ArrayList();
    BluetoothDevice dev;
    BluetoothSocket tmp= null;
    BluetoothSocket mmSocket = null;
    Bundle b;
    int ix;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);
        final BluetoothAdapter bladp = BluetoothAdapter.getDefaultAdapter();
        final Set<BluetoothDevice> pairedDevices = bladp.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                // MAC address
                list.add(deviceName + "\n" + deviceHardwareAddress);
                hwrlst.add(device);

            }
            lstvw = (ListView) findViewById(R.id.devicelist);
            aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            lstvw.setAdapter(aAdapter);
            lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                 ix = i;
                                                 x=(list.get(i)).toString();
                                                final AlertDialog.Builder btn_build;
                                                btn_build = new AlertDialog.Builder(BluetoothList.this);
                                                btn_build.setMessage("Do you want to select "+x+" as OBD Adapter");
                                                btn_build.setCancelable(false);
                                                btn_build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialogInterface, int i) {
                                                         Intent pass = new Intent(getApplicationContext(),connecttodevice.class);
                                                         pass.putExtra("ListIndex",ix);
                                                         startActivity(pass);
                                                     }
                                                 }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialogInterface, int i) {
                                                         dialogInterface.cancel();
                                                     }
                                                 });
                                                 AlertDialog alert = btn_build.create();
                                                 alert.setTitle("Alert");
                                                 alert.show();
                                             }
                                         }
            );
        }
    }

}


