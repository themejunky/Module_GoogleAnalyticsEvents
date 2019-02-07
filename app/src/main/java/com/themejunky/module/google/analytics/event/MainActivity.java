package com.themejunky.module.google.analytics.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import module.themejunky.com.tj_gae.Module_GoogleAnalyticsEvents;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Module_GoogleAnalyticsEvents.getInstance(this,"asdfasd");
    }
}
