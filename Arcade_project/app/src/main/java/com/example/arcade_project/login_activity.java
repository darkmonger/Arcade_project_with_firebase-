package com.example.arcade_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login_activity extends AppCompatActivity {
    Button button;
    private Button mbutton_no_register;

     EditText meditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        meditText = findViewById(R.id.editText_UserName);
        meditText = findViewById(R.id.editText__LoginPassword);

        mbutton_no_register = findViewById(R.id.btn_no_register);
        mbutton_no_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_activity.this,Menu_activity.class));
            }
        });



        button = (Button) findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_activity.this,Register_activity.class));
            }


        });
        button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                startActivity(new Intent(login_activity.this,Menu_activity.class));
            }
        });

    }

}