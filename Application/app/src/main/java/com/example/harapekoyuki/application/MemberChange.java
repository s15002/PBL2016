package com.example.harapekoyuki.application;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MemberChange extends AppCompatActivity implements View.OnClickListener{

    private MyHelper myHelper;

    private Button button_cancel;
    private Button button_change;

    private String ret_msg = "";
    private String err_msg = "";

    Intent intent;

    //列のindex(位置)を取得する
    int lastNameIndex;
    int firstNameIndex;
    int addressIndex;
    int genderIndex;
    int passwordIndex;
    int memberIdIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_change);

        myHelper = new MyHelper(this);

        MemberItemStr item = new MemberItemStr();



        //SQLiteDatabase取得
        SQLiteDatabase db = myHelper.getWritableDatabase();

        item.MemberId = "auau";

        //queryを呼び、検索を行う
        String where = MyHelper.Columns.MEMBERID + "=?";
        String [] args = {item.MemberId};
        Cursor cursor = db.query(
                MyHelper.TABLE_NAME, null, where, args, null, null, null
        );

        if(!cursor.moveToFirst()){
            cursor.close();
            db.close();
            err_msg += "ログインしていません";
            Log.d("Index", "err_msg = " + err_msg);
        } else {

            //列のindex(位置)を取得する
            lastNameIndex = cursor.getColumnIndex(MyHelper.Columns.LASTNAME);
            firstNameIndex = cursor.getColumnIndex(MyHelper.Columns.FIRSTNAME);
            addressIndex = cursor.getColumnIndex(MyHelper.Columns.ADDRESS);
            genderIndex = cursor.getColumnIndex(MyHelper.Columns.GENDER);
            passwordIndex = cursor.getColumnIndex(MyHelper.Columns.PASSWORD);
            memberIdIndex = cursor.getColumnIndex(MyHelper.Columns.MEMBERID);

            item.MemberId = cursor.getString(memberIdIndex);

            err_msg += "ログインしています";
            Log.d("Index", "err_msg = " + err_msg);
        }

        //行を読み込む
        item.LastName = cursor.getString(lastNameIndex);
        item.FirstName = cursor.getString(firstNameIndex);
        item.Address = cursor.getString(addressIndex);
        item.Password = cursor.getString(passwordIndex);
        item.MemberId = cursor.getString(memberIdIndex);


        EditText lastNameEdit = (EditText)findViewById(R.id.edit_lastName);
        EditText firstNameEdit = (EditText)findViewById(R.id.edit_firstName);
        EditText addressEdit = (EditText)findViewById(R.id.edit_address);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_gender);
        EditText passwordEdit = (EditText)findViewById(R.id.edit_password);

        Log.d("MemberChange", "LastName = "+ item.LastName );

        lastNameEdit.setText(item.LastName);
        firstNameEdit.setText(item.FirstName);
        addressEdit.setText(item.Address);
        passwordEdit.setText(item.Password);


        cursor.close();
        db.close();






        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_change = (Button)findViewById(R.id.button_change);
        button_change.setOnClickListener(this);
    }


    private class MemberItemStr{
        String LastName;
        String FirstName;
        String Address;
        String Gender;
        String Password;
        String MemberId;
    }


    public void onClick(View v){
        if (v.getId() == R.id.button_cancel){

            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);

        } else if (v.getId() == R.id.button_change) {

            MemberItemStr item = new MemberItemStr();

            EditText edit_lastName = (EditText)findViewById(R.id.edit_lastName);
            item.LastName = edit_lastName.getText().toString();
            if(item.LastName.equals("")){
                err_msg += "姓を入力してください\n";
            }

            EditText edit_firstName = (EditText)findViewById(R.id.edit_firstName);
            item.FirstName = edit_firstName.getText().toString();
            if(item.FirstName.equals("")){
                err_msg += "名を入力してください\n";
            }

            EditText edit_address = (EditText)findViewById(R.id.edit_address);
            item.Address = edit_address.getText().toString();
            if(item.Address.equals("")){
                err_msg += "住所を入力してください\n";
            }

            EditText edit_password = (EditText)findViewById(R.id.edit_password);
            item.Password = edit_password.getText().toString();
            if(item.Password.equals("")){
                err_msg += "パスワードを入力してください\n";
            }

            if(err_msg.equals("")){

                ret_msg = updateMember(item);

                intent = new  Intent(this, MainActivity.class);
                startActivityForResult(intent, 1);

                if(ret_msg.equals("")){
                    intent = new Intent(this, MainActivity.class);
                    startActivityForResult(intent, 11);

                }else{

                    //確認ダイアログの作成
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

                    alertDlg.setTitle("会員編集");

                    alertDlg.setMessage(ret_msg);
                    alertDlg.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public  void onClick(DialogInterface dialogInterface, int i){
                                    //登録ボタンクリックの処理
                                }
                            }
                    );
                    //表示
                    alertDlg.create().show();
                }
            }else {
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

                alertDlg.setTitle("会員編集");

                alertDlg.setMessage(err_msg);
                alertDlg.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //OKボタンクリック処理
                            }
                        }
                );
                //表示
                alertDlg.create().show();
            }
        }
    }


    private String updateMember(MemberItemStr item){

       //SQLiteDatabase取得
        SQLiteDatabase db = myHelper.getWritableDatabase();

        //queryを呼び、検索を行う
        String where = MyHelper.Columns._ID + "=?";
        String [] args = {item.MemberId};
        Cursor cursor = db.query(
                MyHelper.TABLE_NAME, null, where, args, null, null, null
        );



        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            err_msg += "ログインしていません";
            return err_msg;
        } else {
            //列のindex(位置)を取得する
            lastNameIndex = cursor.getColumnIndex(MyHelper.Columns.LASTNAME);
            firstNameIndex = cursor.getColumnIndex(MyHelper.Columns.FIRSTNAME);
            addressIndex = cursor.getColumnIndex(MyHelper.Columns.ADDRESS);
            genderIndex = cursor.getColumnIndex(MyHelper.Columns.GENDER);
            passwordIndex = cursor.getColumnIndex(MyHelper.Columns.PASSWORD);
            memberIdIndex = cursor.getColumnIndex(MyHelper.Columns.MEMBERID);

            item.MemberId = cursor.getString(memberIdIndex);
            cursor.close();
            db.close();
        }



        //更新する値をセット
        ContentValues values = new ContentValues();

        values.put(MyHelper.Columns.LASTNAME, item.LastName);
        values.put(MyHelper.Columns.FIRSTNAME, item.LastName);
        values.put(MyHelper.Columns.ADDRESS, item.Address);
        values.put(MyHelper.Columns.GENDER, item.Gender);
        values.put(MyHelper.Columns.PASSWORD, item.Password);



        //更新する行をWHEREで指定
        int count = db.update(MyHelper.TABLE_NAME, values, where, args);

        //Update出来なかったらLog
        if (count == 0){
            Log.d ("Edit", "Failed to update");
        }

        //データベースを閉じる
        db.close();

        return err_msg;
    }
}
