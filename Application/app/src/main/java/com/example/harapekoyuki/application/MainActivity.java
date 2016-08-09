package com.example.harapekoyuki.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_create = (Button)findViewById(R.id.button_create);
        button_create.setOnClickListener(this);
    }

    public void onClick(View v){
        if (v == button_create){
            Intent intent = new Intent(this, MemberRegistration.class);
            startActivityForResult(intent, 0);
        }
    }
}
