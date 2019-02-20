package com.valjapan.realmlistapp;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        // Realmは必ず使用するときRealm.initをしないと使用ができない。
        // どのActivityからも自由に値を参照したい時にApplicationクラスを使う。
        // Applicationクラスを使うとアプリケーション内でデータの共有が可能。
        // このクラスはアプリ起動と同時に作成され動作する。
        // イメージとしてはどこからでも参照できる最上位のメモリ領域を確保している感じになる。

        // AndroidManifest.xmlのapplicationの属性に、
        // android:name="アプリケーションのクラス名"を記述することを忘れずに。

    }
}