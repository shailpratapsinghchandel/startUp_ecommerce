package com.example.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNowButton =(Button) findViewById(R.id.join_now_btn);
        LoginButton=(Button) findViewById(R.id.main_login_btn);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Login_Activity.class);
                        startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent=new Intent(MainActivity.this, Register_activity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );

    }
}
