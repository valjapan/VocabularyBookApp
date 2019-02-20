package com.valjapan.realmlistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class ListAdapter extends ArrayAdapter<Tango> {
    //AdapterはListViewを橋渡しする役目を持っている

    private LayoutInflater layoutinflater;

    ListAdapter(Context context, int textViewResourceId, List<Tango> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final Tango tango = getItem(position);
        // getItem(position)でTangoクラスに入っている内容からposition番目のデータを取得する

        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.layout_item_tango, null);
            // itemをセットする(ListViewに表示する1個のセルのテンプレートのようなもの)
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.content1_text);
        //そのセルのViewの関連付け


        if (tango != null) { //もし、tangoの内容に何か入っていれば、

            textView.setText(tango.japan);
            //初期を日本語表示させる

            textView.setOnClickListener(new View.OnClickListener() {
                //setOnClickListener タップしたら反応する内容を記入する
                @Override
                public void onClick(View v) {

                    Realm realm = Realm.getDefaultInstance();
                    // Realmを使うのに必ず書かないといけない

                    final Tango realmTango = realm.where(Tango.class).equalTo("updateDate", tango.updateDate).findFirst();
                    //詳しくはAddActivityを参照
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            if (realmTango.judge) {
                                //textViewを日本語にsetTextする
                                textView.setText(tango.japan);
                                realmTango.judge = false;
                            } else {
                                //textViewを英語にsetTextする
                                textView.setText(tango.english);
                                realmTango.judge = true;
                            }
                        }
                    });
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) { //長押ししたら

                    final Context context = getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("updateDate", tango.updateDate);
                    context.startActivity(intent);
                    // DetailActivityに画面推移する

                    return false;
                }
            });

        }

        return convertView;
    }
}
