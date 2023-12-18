package com.example.arcade_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Menu_activity extends AppCompatActivity {
    ImageView imageView;;
    Button mbutton_games,mbutton_info,mbutton_;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mbutton_games = findViewById(R.id.button_games);
        mbutton_info = findViewById(R.id.button_information);
        mbutton_ = findViewById(R.id.button_exit);


        mbutton_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_activity.this, ArcadeGames_activity.class);
                startActivity(intent);
            }
        });


        mbutton_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_activity.this,Maps_Activity.class));
            }
        });
        mbutton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_wbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id._menu_login:
                startActivity(new Intent(Menu_activity.this,login_activity.class));
                 return true;
            case R.id._menu_information:
                startActivity(new Intent(Menu_activity.this,Information_Activity.class));
                return true;
            case R.id._menu_maps:
                startActivity(new Intent(Menu_activity.this,Maps_Activity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}