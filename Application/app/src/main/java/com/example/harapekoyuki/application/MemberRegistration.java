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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MemberRegistration extends AppCompatActivity implements View.OnClickListener{

    private MyHelper myHelper;

    private Button button_cancel;
    private Button button_register;

    private String ret_msg = "";
    private String err_msg = "";

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registration);

        //MyHelperを作り、フィールドにセット
        myHelper = new MyHelper(this);

        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_register = (Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

    }


    private class MemberItemStr {
        String MemberId;
        String LastName;
        String FirstName;
        String Address;
        String Gender;
        String Year;
        String Month;
        String Day;
        String Password;

    }


    public void onClick(View v) {

        if (v.getId() == R.id.button_cancel) {

            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 10);

        } else if (v.getId() == R.id.button_register) {

            MemberItemStr item = new MemberItemStr();

            EditText edit_id = (EditText)findViewById(R.id.edit_id);
            item.MemberId = edit_id.getText().toString();
            if(item.MemberId.equals("")){
                err_msg += "会員IDを入力してください\n";
            }

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

            RadioGroup rg = (RadioGroup)findViewById(R.id.radio_gender);
            int id = rg.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton)findViewById(id);
            item.Gender = radioButton.getText().toString();
            Log.d("RadioButton", "msg = " + item.Gender);

            EditText edit_password = (EditText)findViewById(R.id.edit_password);
            item.Password = edit_password.getText().toString();
            if(item.Password.equals("")){
                err_msg += "パスワードを入力してください\n";
            }

            if(err_msg.equals("")){

                ret_msg = insertMember(item);

                if(ret_msg.equals("")){
                    intent = new Intent(this, MainActivity.class);
                    startActivityForResult(intent, 11);

                }else{

                    //確認ダイアログの生成
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

                    alertDlg.setTitle("会員登録");

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
            }else{
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

                alertDlg.setTitle("会員登録");

                alertDlg.setMessage(err_msg);
                alertDlg.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                //OKボタンクリック処理
                            }
                        }
                );

                //表示
                alertDlg.create().show();
            }
        }
    }


    private String insertMember(MemberItemStr item){

        SQLiteDatabase db_q = myHelper.getWritableDatabase();

        String where = MyHelper.Columns.MEMBERID + "=?";
        String [] args = {item.MemberId};
        Cursor cursor = db_q.query(
                MyHelper.TABLE_NAME, null, where, args, null, null, null
        );

        if(cursor.moveToFirst()){
            cursor.close();
            db_q.close();
            err_msg += "その会員IDはすでに存在しています";
            return err_msg;
        } else {
            cursor.close();
            db_q.close();
        }

        SQLiteDatabase db = myHelper.getWritableDatabase();

        // 列に対応する値をセット
        ContentValues values = new ContentValues();

        values.put(MyHelper.Columns.LASTNAME, item.LastName);
        values.put(MyHelper.Columns.FIRSTNAME, item.FirstName);
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
