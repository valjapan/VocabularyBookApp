package com.valjapan.realmlistapp;

import io.realm.RealmObject;



public class Tango extends RealmObject { //デフォルトのクラスを作る
    public String japan;
    public String english;
    public String updateDate;
    public Boolean judge;

    public Tango() {
    }

    public Tango(String japan, String english, String updateDate, Boolean judge) {
        this.japan = japan;
        this.english = english;
        this.updateDate = updateDate;
        this.judge = judge;
    }

}
