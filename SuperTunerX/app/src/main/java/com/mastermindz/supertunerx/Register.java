package com.mastermindz.supertunerx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private Button btx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btx=findViewById(R.id.btreg);
        btx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(p);
                Toast.makeText(getApplicationContext(),"Registration Successsful",Toast.LENGTH_LONG).show();
            }
        });
    }
}
