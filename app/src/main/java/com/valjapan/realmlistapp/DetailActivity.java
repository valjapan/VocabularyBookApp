package com.valjapan.realmlistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    public Realm realm;

    public EditText japanEditText;
    public EditText englishEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        // Realmを使うのに必ず書かないといけない

        japanEditText = (EditText) findViewById(R.id.japanDetailEditText);
        englishEditText = (EditText) findViewById(R.id.englishDetailEditText);
        // Viewとの関連付け

        showData();
        //showDataを実行
    }

    public void showData() {

        final Tango tango = realm.where(Tango.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();
        // realm.where(クラス名)でrealmからTangoクラス型の情報を取得する
        // equalTo("変数名","探す値")でrealmから"変数名"が"探す値"と一致するデータを検索する
        // getIntent.getStringExtra("推移時の名前")でListAdapterで書いた、putExtraで書いた名前と一致させることで、
        // 値を取得することができる。
        // (ListAdapterから抜粋)intent.putExtra("updateDate", tango.updateDate);
        // findFirst()検索して見つかった要素から一番初めにヒットした結果を返す

        japanEditText.setText(tango.japan);
        englishEditText.setText(tango.english);
        //取得した情報をeditTextにsetする
    }

    public void edit(View view) {

        final Tango tango = realm.where(Tango.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();
        // 編集完了ボタンが押されたら、同じUpdateDateのデータを受け取る

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tango.japan = japanEditText.getText().toString();
                tango.english = englishEditText.getText().toString();
                //日本語、英語をsetする(updateDateとjudgeは変更しないから書かない)
            }
        });

        finish();
        // Activityを終了する
    }

    public void delete(View view) {

        final Tango tango = realm.where(Tango.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();
        // 編集完了ボタンが押されたら、同じUpdateDateのデータを受け取る

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tango.deleteFromRealm();
                //削除
            }
        });

        finish();
        // Activityを終了する
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
        //Activity終了時にRealmはrealm.closeで閉じないといけない。
    }


}
