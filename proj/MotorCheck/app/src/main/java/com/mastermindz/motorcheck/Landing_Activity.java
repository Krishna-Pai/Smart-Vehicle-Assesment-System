package com.mastermindz.motorcheck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Landing_Activity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_);
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
            else {
                Intent x = new Intent(getApplicationContext(),BluetoothList.class);
                startActivity(x);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Bluetooth is enabled", Toast.LENGTH_LONG).show();
            Intent p = new Intent(getApplicationContext(),BluetoothList.class);
            startActivity(p);

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Please enable bluetooth", Toast.LENGTH_LONG).show();
        }
    }
}
