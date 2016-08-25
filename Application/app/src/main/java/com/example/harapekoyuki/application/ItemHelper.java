package com.example.harapekoyuki.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by s14002 on 16/08/23.
 */
public class ItemHelper extends SQLiteOpenHelper {
    //テーブルの作成
    private static final String DB_NAME = "Product.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "ProductTable_db";
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    Columns._ID + " INTEGER primary key autoincrement," +
                    Columns.PRODUCT_ID + " INTEGER primary key autoincrement," +
                    Columns.PRODUCT_NAME + " TEXT," +
                    Columns.PRICE + " INTEGER," +
                    Columns.STOCK + " INTEGER," +
                    Columns.PRESENT + " INTEGER," +
                    Columns.IMAGE + " BLOB)";

    //列の名前を宣言
    public interface Columns extends BaseColumns {
        public static final String PRODUCT_ID = "ProductId";
        public static final String PRODUCT_NAME = "ProductName";
        public static final String PRICE = "Price";
        public static final String STOCK = "Stock";
        public static final String PRESENT = "Present";
        public static final String IMAGE = "Image";

    }

    //コンストラクターメソッド、instanceの初期化
    public ItemHelper(Context context){

        super (context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("ItemHelper", "onCreate");

        db.execSQL(SQL_CREATE_TABLE);
    }

    //SQLiteに含まれいるため、空だけど実装
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il){
    }
}
