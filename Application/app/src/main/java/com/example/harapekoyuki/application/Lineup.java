package com.example.harapekoyuki.application;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Lineup extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase db;
    private ItemHelper itemHelper;
    private Button button_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        button_return = (Button) findViewById(R.id.button_return);
        button_return.setOnClickListener(this);

        //MyHelperを作り、フィールドにセット
        itemHelper = new ItemHelper(this);

        // 読み書き出来るように開く
        db = itemHelper.getWritableDatabase();

        // 画像読み込み
        /**
        ByteArrayOutputStream product0 = getByteArrayOutputStream(String.valueOf(R.drawable.product_0));
        ByteArrayOutputStream product1 = getByteArrayOutputStream(String.valueOf(R.drawable.product_1));
        ByteArrayOutputStream product2 = getByteArrayOutputStream(String.valueOf(R.drawable.product_2));
        ByteArrayOutputStream product3 = getByteArrayOutputStream(String.valueOf(R.drawable.product_3));
        ByteArrayOutputStream product4 = getByteArrayOutputStream(String.valueOf(R.drawable.product_4));
        ByteArrayOutputStream product5 = getByteArrayOutputStream(String.valueOf(R.drawable.product_5));
        ByteArrayOutputStream product6 = getByteArrayOutputStream(String.valueOf(R.drawable.product_6));
        ByteArrayOutputStream product7 = getByteArrayOutputStream(String.valueOf(R.drawable.product_7));
        ByteArrayOutputStream product8 = getByteArrayOutputStream(String.valueOf(R.drawable.product_8));



        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values0 = new ContentValues();
        values0.put(ItemHelper.Columns.IMAGE,
                product0.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values1 = new ContentValues();
        values1.put(ItemHelper.Columns.IMAGE,
                product1.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values2 = new ContentValues();
        values2.put(ItemHelper.Columns.IMAGE,
                product2.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values3 = new ContentValues();
        values3.put(ItemHelper.Columns.IMAGE,
                product3.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values4 = new ContentValues();
        values4.put(ItemHelper.Columns.IMAGE,
                product4.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values5 = new ContentValues();
        values5.put(ItemHelper.Columns.IMAGE,
                product5.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values6 = new ContentValues();
        values6.put(ItemHelper.Columns.IMAGE,
                product6.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values7 = new ContentValues();
        values7.put(ItemHelper.Columns.IMAGE,
                product7.toByteArray());

        // レコード設定 ByteArrayOutputStreamをbyte[]に変換し格納
        ContentValues values8 = new ContentValues();
        values8.put(ItemHelper.Columns.IMAGE,
                product8.toByteArray());


        // レコードを検索してカーソルを作成(SQL)
        Cursor cursor = db.query(ItemHelper.TABLE_NAME,
                new String[]{"_id",
                        ItemHelper.Columns.IMAGE}, null,
                null, null, null, null);

        // カーソルから値を取り出す
        while (cursor.moveToNext()) {
            // 表示用LinearLayout
            LinearLayout linear = new LinearLayout(this);
            // idとファイル名を受け取りTextViewとして表示
            String str = cursor.getString(cursor.getColumnIndex("_id"))
                    + "\t"
                    + cursor.getString(cursor.getColumnIndex(ItemHelper.Columns.IMAGE));


            TextView tv = new TextView(this);
            tv.setText(str);
            // BLOBをbyte[]で受け取る.
            byte blob[] = cursor
                    .getBlob(cursor
                            .getColumnIndex(ItemHelper.Columns.IMAGE));
            // byte[]をビットマップに変換しImageViewとして表示
            Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(bmp);
            linear.addView(tv);
            linear.addView(iv);

        }


        // カーソルクローズ
        cursor.close();
        // DBクローズ
        db.close();
        // MySQLiteOpenHelperクローズ
        itemHelper.close();
        // outputStreamのクローズ
        try {
            product0.close();
            product1.close();
            product2.close();
            product3.close();
            product4.close();
            product5.close();
            product6.close();
            product7.close();
            product8.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

**/
    }

    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.button_return) {
            intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        }

    }

    /**
     * ファイル名を受け取りByteArrayOutputStreamを返却.
     *
     * @param fileName
     * @return ByteArrayOutputStream
     */

    private ByteArrayOutputStream getByteArrayOutputStream(String fileName) {
        // 返却するByteArrayOutputStream
        ByteArrayOutputStream retStream = new ByteArrayOutputStream();
        try {
            // assetsから画像ファイルを読み込みBufferedInputStreamを作成
            BufferedInputStream iS = new BufferedInputStream(getAssets().open(
                    fileName));
            // 書き込み用int
            int writeInt;
            // ByteArrayOutputStreamに画像ファイルを書き込む
            while ((writeInt = iS.read()) != -1) {
                retStream.write(writeInt);
            }
            // inputstreamのクローズ
            try {
                iS.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retStream;
    }
}

