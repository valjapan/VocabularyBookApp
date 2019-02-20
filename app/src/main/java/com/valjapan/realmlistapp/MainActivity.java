package com.valjapan.realmlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    public Realm realm;
    public Tango tango;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tango = new Tango();
        //Tangoをインスタンス

        realm = Realm.getDefaultInstance();

        listView = findViewById(R.id.list_view);
        //listviewを関連付け

        FloatingActionButton fab = findViewById(R.id.fab);
        // Buttonを右下に置く
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                // AddActivityに画面推移する
                startActivity(intent);
            }
        });
    }

    public void addListView() {
        final RealmResults<Tango> list = realm.where(Tango.class).findAll();
        List<Tango> item = realm.copyFromRealm(list);
        // itemにtangoを代入する

        ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.layout_item_tango, item);
        listView.setAdapter(adapter);
        //addAdapterをsetする
    }

    @Override
    protected void onResume() {
        super.onResume();
        addListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
