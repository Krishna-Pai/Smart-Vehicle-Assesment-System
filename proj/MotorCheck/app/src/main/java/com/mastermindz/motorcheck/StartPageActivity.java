package com.mastermindz.motorcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StartPageActivity extends AppCompatActivity implements View.OnClickListener {
    Button glelogin,emlogin;
    TextView register;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        emlogin=(Button)findViewById(R.id.button2);
        register=(TextView)findViewById(R.id.textView2);
        email=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        register.setOnClickListener(this);
        emlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(emlogin==view)
        {
            Intent j = new Intent(getApplicationContext(),Landing_Activity.class);
            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
            startActivity(j);
        }

        if(register==view)
        {
            Intent p = new Intent(getApplicationContext(),Register.class);
            Toast.makeText(getApplicationContext(),"Getting you to Registration Page",Toast.LENGTH_LONG).show();
            startActivity(p);
        }

    }
}
