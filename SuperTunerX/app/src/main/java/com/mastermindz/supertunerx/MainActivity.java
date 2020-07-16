package com.mastermindz.supertunerx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent x = getPackageManager().getLaunchIntentForPackage("com.github.pires.obd.reader");
                    startActivity(x);
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(getApplicationContext(),Register.class);
                startActivity(y);
            }
        });
    }
}
