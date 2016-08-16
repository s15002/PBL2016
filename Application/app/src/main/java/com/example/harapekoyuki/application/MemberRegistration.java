package com.example.harapekoyuki.application;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MemberRegistration extends AppCompatActivity implements View.OnClickListener{

    private MyHelper myHelper;

    private Button button_cancel;
    private Button button_register;

    private int _id;


    private String mode = "";
    private  String err_msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registration);

        Intent intent = getIntent();

        mode  = intent.getStringExtra("mode");

        EditText surnameEdit = (EditText)findViewById(R.id.edit_firstName);
        EditText nameEdit = (EditText)findViewById(R.id.edit_lastName);
        EditText addressEdit = (EditText)findViewById(R.id.edit_address);
        EditText passwordEdit = (EditText)findViewById(R.id.edit_password);
        EditText memberIdEdit = (EditText)findViewById(R.id.edit_id);

        if(mode.equals("")){

            _id = intent.getIntExtra("_id", 0);
            String Surname = intent.getStringExtra("Surname");
            String Name = intent.getStringExtra("Name");
            String Address = intent.getStringExtra("Address");
            String Gender = intent.getStringExtra("gender");
            int Year = intent.getIntExtra("Year", 0);
            int Month = intent.getIntExtra("Month", 0);
            int Day = intent.getIntExtra("Day", 0);
            String Password = intent.getStringExtra("Password");
            String MemberId = intent.getStringExtra("MemberId");
        }




        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_register = (Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

    }


    private class MemberItemStr {
        String Surname;
        String Name;
        String Address;
        String Gender;
        String Year;
        String Month;
        String Day;
        String Password;
        String MemberId;
    }


    public void onClick(View v) {

        MemberItemStr item = new MemberItemStr();

        Intent intent;

        if (v.getId() == R.id.button_cancel) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 10);
        } else if (v.getId() == R.id.button_register) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 11);
        }
    }


    private String insertMember(MemberItemStr item){

        SQLiteDatabase db = myHelper.getWritableDatabase();

        // 列に対応する値をセット
        ContentValues values = new ContentValues();

        values.put(MyHelper.Columns.SURNAME, item.Surname);
        values.put(MyHelper.Columns.NAME, item.Name);
        values.put(MyHelper.Columns.ADDRESS, item.Address);
        values.put(MyHelper.Columns.GENDER, item.Gender);
        values.put(MyHelper.Columns.YEAR, item.Year);
        values.put(MyHelper.Columns.MONTH, item.Month);
        values.put(MyHelper.Columns.DAY, item.Day);
        values.put(MyHelper.Columns.PASSWORD, item.Password);
        values.put(MyHelper.Columns.MEMBERID, item.MemberId);

        // データベースに行を追加する
        long id = db.insert(MyHelper.TABLE_NAME, null, values);
        if(id == -1){
            Log.d("Database", "行の追加に失敗したよ");
        }

        // データベースを閉じる(処理の終了を伝える)
        db.close();

        return err_msg;

    }


}
