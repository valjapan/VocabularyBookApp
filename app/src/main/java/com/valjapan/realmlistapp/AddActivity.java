package com.valjapan.realmlistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity {

    public Realm realm;
    public Tango tango;

    public EditText japanEditText;
    public EditText englishEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tango = new Tango();
        // Tangoクラスをインスタンス

        realm = Realm.getDefaultInstance();
        // Realmを使うのに必ず書かないといけない

        japanEditText = (EditText) findViewById(R.id.japanEditText);
        englishEditText = (EditText) findViewById(R.id.englishEditText);
        // Viewとの関連付け
    }

    public void save(View v) {
        Date date = new Date();
        //Dateクラスをインスタンス
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE);
        //現在時間を取得
        final String updateDate = sdf.format(date);
        //SimpleDateFormatをStringに変換

        final String japan = japanEditText.getText().toString();
        final String english = englishEditText.getText().toString();
        // EditTextに記入した内容をStringに代入

        final Boolean judge = false;
        // 日本語か英語かを表示させるためのBoolean型(falseは日本語,trueは英語を表示)

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tango = realm.createObject(Tango.class);
                //Tangoクラスに情報を代入してあげると自動的に保存してくれる。
                tango.japan = japan;
                tango.english = english;
                tango.updateDate = updateDate;
                tango.judge = judge;
            }
        });

        finish();
        //AddActivityを閉じる
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        //Activity終了時にRealmはrealm.closeで閉じないといけない。
    }
}
