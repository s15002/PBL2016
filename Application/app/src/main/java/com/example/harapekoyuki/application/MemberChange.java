package com.example.harapekoyuki.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberChange extends AppCompatActivity implements View.OnClickListener{

    private Button button_cancel;
    private Button button_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_change);

        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_change = (Button)findViewById(R.id.button_change);
        button_change.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent;
        if (v.getId() == R.id.button_cancel){
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        } else if (v.getId() == R.id.button_change){
            intent = new  Intent(this, MainActivity.class);
            startActivityForResult(intent, 1);
        }

    }
}
