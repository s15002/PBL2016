package com.example.harapekoyuki.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberRegistration extends AppCompatActivity implements View.OnClickListener{

    private Button button_cancel;
    private Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registration);

        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_register = (Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(this);
    }
    public void onClick(View v) {

        Intent intent;

        if (v.getId() == R.id.button_cancel) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 10);
        } else if (v.getId() == R.id.button_register) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 11);
        }
    }
}
