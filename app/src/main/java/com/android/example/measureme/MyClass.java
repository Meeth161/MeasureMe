package com.android.example.measureme;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class MyClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
