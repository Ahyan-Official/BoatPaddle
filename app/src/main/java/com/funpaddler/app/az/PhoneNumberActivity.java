package com.funpaddler.app.az;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhoneNumberActivity extends AppCompatActivity {


    Button startNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);


        startNow = findViewById(R.id.startNow);


        startNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhoneNumberActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}