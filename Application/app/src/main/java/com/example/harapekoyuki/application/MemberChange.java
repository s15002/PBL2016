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

    //インスタンスクラス生成
    private class MemberItemIndex{
        int lastNameIndex;
        int firstNameIndex;
        int addressIndex;
        int genderIndex;
        int passwordIndex;
        int memberIdIndex;
    }

    //インスタンスクラス生成
    private class MemberItemStr{
        String LastName;
        String FirstName;
        String Address;
        String Gender;
        String Password;
        String MemberId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_change);

        //buttonObjectを取得し、Clickした時の処理をOnClickクラスに移動
        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);

        button_change = (Button)findViewById(R.id.button_change);
        button_change.setOnClickListener(this);

        //MemberItemIndexクラスを取得
        MemberItemIndex index = new MemberItemIndex();

        //MemberItemStrクラスを取得
        MemberItemStr item = new MemberItemStr();

        //MyHelperファイルを取得
        myHelper = new MyHelper(this);

        //SQLiteDatabase取得
        SQLiteDatabase db = myHelper.getWritableDatabase();

        //IDを指定
        item.MemberId = "Hello";

        //queryを呼び、検索を行う
        String where = MyHelper.Columns.MEMBERID + "=?";
        String [] args = {item.MemberId};
        Cursor cursor = db.query(
                MyHelper.TABLE_NAME, null, where, args, null, null, null
        );

        //指定したIDに引っかかったらif,引っかからなかったらelse
        if(cursor.moveToFirst()){

            //列のindex(位置)を取得する
            index.lastNameIndex = cursor.getColumnIndex(MyHelper.Columns.LASTNAME);
            index.firstNameIndex = cursor.getColumnIndex(MyHelper.Columns.FIRSTNAME);
            index.addressIndex = cursor.getColumnIndex(MyHelper.Columns.ADDRESS);
            index.genderIndex = cursor.getColumnIndex(MyHelper.Columns.GENDER);
            index.passwordIndex = cursor.getColumnIndex(MyHelper.Columns.PASSWORD);
            index.memberIdIndex = cursor.getColumnIndex(MyHelper.Columns.MEMBERID);

            item.MemberId = cursor.getString(index.memberIdIndex);

        } else {

            //cursorとdbを閉じてエラーメッセージ
            cursor.close();
            db.close();
        }

        //行を読み込む
        item.LastName = cursor.getString(index.lastNameIndex);
        item.FirstName = cursor.getString(index.firstNameIndex);
        item.Address = cursor.getString(index.addressIndex);
        item.Password = cursor.getString(index.passwordIndex);
        item.MemberId = cursor.getString(index.memberIdIndex);

        //各EditObjectの取得
        EditText lastNameEdit = (EditText)findViewById(R.id.edit_lastName);
        EditText firstNameEdit = (EditText)findViewById(R.id.edit_firstName);
        EditText addressEdit = (EditText)findViewById(R.id.edit_address);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_gender);
        EditText passwordEdit = (EditText)findViewById(R.id.edit_password);

        //item.LastNameに値が入っているかの確認
        Log.d("MemberChange", "LastName = "+ item.LastName );

        //EditTextにDBから取得した値を入れる
        lastNameEdit.setText(item.LastName);
        firstNameEdit.setText(item.FirstName);
        addressEdit.setText(item.Address);
        passwordEdit.setText(item.Password);

        //cursorとdbを閉じる
        cursor.close();
        db.close();

    }


    public void onClick(View v){

        //button_cancelが押されたらif,button_changeが押されたらelse
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

        //MemberItemIndexクラスを取得
        MemberItemIndex index = new MemberItemIndex();

       //SQLiteDatabase取得
        SQLiteDatabase db_u = myHelper.getWritableDatabase();

        //IDを指定
        item.MemberId = "Hello";

       //queryを呼び、検索を行う
        String where = MyHelper.Columns.MEMBERID + "=?";
        String [] args = {item.MemberId};
        Cursor cursor = db_u.query(
                MyHelper.TABLE_NAME, null, where, args, null, null, null
        );

        //指定したIDに引っかかったらif,引っかからなかったらelse
        if(cursor.moveToFirst()){

            //列のindex(位置)を取得する
            index.lastNameIndex = cursor.getColumnIndex(MyHelper.Columns.LASTNAME);
            index.firstNameIndex = cursor.getColumnIndex(MyHelper.Columns.FIRSTNAME);
            index.addressIndex = cursor.getColumnIndex(MyHelper.Columns.ADDRESS);
            index.genderIndex = cursor.getColumnIndex(MyHelper.Columns.GENDER);
            index.passwordIndex = cursor.getColumnIndex(MyHelper.Columns.PASSWORD);
            index.memberIdIndex = cursor.getColumnIndex(MyHelper.Columns.MEMBERID);

            item.MemberId = cursor.getString(index.memberIdIndex);

            ret_msg += "変更しました";
            Log.d("Login", "ret_msg = " + ret_msg);

        } else {

            //cursorとdbを閉じてエラーメッセージ
            cursor.close();
            db_u.close();
            err_msg += "ログイン情報がありませんでした";
            Log.d("Login", "err_msg = " + err_msg);
        }

        //更新する値をセット
        ContentValues values = new ContentValues();

        values.put(MyHelper.Columns.LASTNAME, item.LastName);
        values.put(MyHelper.Columns.FIRSTNAME, item.LastName);
        values.put(MyHelper.Columns.ADDRESS, item.Address);
        values.put(MyHelper.Columns.GENDER, item.Gender);
        values.put(MyHelper.Columns.PASSWORD, item.Password);


        //更新する行をWHEREで指定
        int count = db_u.update(MyHelper.TABLE_NAME, values, where, args);

        //Update出来なかったらLog
        if (count == 0){
            Log.d ("Edit", "Failed to update");
        }

        //データベースを閉じる
        db_u.close();

        return ret_msg;
    }
}
