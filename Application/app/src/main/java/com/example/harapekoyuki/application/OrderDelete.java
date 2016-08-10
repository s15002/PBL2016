package com.example.harapekoyuki.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderDelete extends AppCompatActivity implements View.OnClickListener {

    private Button button_yes;
    private Button button_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delete);

        button_yes = (Button) findViewById(R.id.button_yes);
        button_yes.setOnClickListener(this);

        button_no = (Button) findViewById(R.id.button_no);
        button_no.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.button_yes) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        } else if (v.getId() == R.id.button_no) {
            intent = new Intent(this, OrderStatus.class);
            startActivityForResult(intent, 1);
        }

    }
}