package com.mastermindz.assessment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity{
    private static final int REQUEST_ENABLE_BT = 2;
    private String TAG = "request message";
    private ListView lstvw;
    private ArrayAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Do bluetooth enabling while login only (merge login first)
        BluetoothAdapter bladp = BluetoothAdapter.getDefaultAdapter();
        if (bladp == null) {
            Toast.makeText(getApplicationContext(), "Your device probably doesn't " +
                    "support bluetooth", Toast.LENGTH_LONG).show();
            finishAffinity();
            System.exit(0);
        } else {
            if (!bladp.isEnabled()) {
                Intent enablebtint = new Intent(bladp.ACTION_REQUEST_ENABLE);
                startActivityForResult(enablebtint, REQUEST_ENABLE_BT);
            }
            else{
                //Doesn't show list when enabled but shown when already enabled
                //Use the else block in a new activity to check paired devices
                Set<BluetoothDevice> pairedDevices = bladp.getBondedDevices();
                ArrayList list = new ArrayList();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        list.add(deviceName+"\n"+deviceHardwareAddress);
                    }
                    lstvw=(ListView)findViewById(R.id.devicelist);
                    aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    lstvw.setAdapter(aAdapter);
                }
            }
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Bluetooth is enabled", Toast.LENGTH_LONG).show();

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Please enable bluetooth", Toast.LENGTH_LONG).show();
        }
    }
}
