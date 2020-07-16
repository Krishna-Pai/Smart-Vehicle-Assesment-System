package com.mastermindz.motorcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button b1;
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent px = new Intent(getApplicationContext(),StartPageActivity.class);
                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                startActivity(px);
            }
        });
    }
}
