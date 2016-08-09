package com.example.harapekoyuki.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_create;
    private Button button_lineup;
    private Button button_orderStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_create = (Button)findViewById(R.id.button_create);
        button_create.setOnClickListener(this);

        button_lineup = (Button)findViewById(R.id.button_lineup);
        button_lineup.setOnClickListener(this);

        button_orderStatus = (Button)findViewById(R.id.button_orderStatus);
        button_orderStatus.setOnClickListener(this);

    }

    public void onClick(View v){

        Intent intent;

        if (v.getId() == R.id.button_create){
            intent = new Intent(this, MemberRegistration.class);
            startActivityForResult(intent, 0);
        }else if(v.getId() == R.id.button_lineup){
            intent = new Intent(this, OrderRegistration.class);
            startActivityForResult(intent, 1);
        }else if(v.getId() == R.id.button_orderStatus){
            intent = new Intent(this, OrderStatus.class);
            startActivityForResult(intent, 2);
        }
    }
}
