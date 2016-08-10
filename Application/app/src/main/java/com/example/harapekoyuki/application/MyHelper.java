package com.example.harapekoyuki.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by harapekoyuki on 2016/08/10.
 */
public class MyHelper extends SQLiteOpenHelper{

    //テーブルの作成
    private static final String DB_NAME = "Test.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "MemberMaster_db";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    Columns._ID + " INTEGER primary key autoincrement," +
                    Columns.SURNAME + " TEXT," +
                    Columns.NAME + " TEXT," +
                    Columns.ADDRESS + " TEXT," +
                    Columns.GENDER + " TEXT," +
                    Columns.YEAR + " INTEGER," +
                    Columns.MONTH + " INTEGER," +
                    Columns.DAY + " INTEGER," +
                    Columns.PASSWORD + " TEXT," +
                    Columns.MEMBERID + " TEXT)";

    //列の名前を宣言
    public interface Columns extends BaseColumns{
        public static final String SURNAME = "Surname";
        public static final String NAME = "Name";
        public static final String ADDRESS = "Address";
        public static final String GENDER = "Gender";
        public static final String YEAR = "Year";
        public static final String MONTH = "Month";
        public static final String DAY = "Day";
        public static final String PASSWORD = "Password";
        public static final String MEMBERID = "MemberId";

    }

    //コンストラクターメソッド、instanceの初期化
    public MyHelper(Context context){

        super (context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("MyHelper", "onCreate");

        db.execSQL(SQL_CREATE_TABLE);
    }

    //SQLiteに含まれいるため、空だけど実装
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il){
    }
}
