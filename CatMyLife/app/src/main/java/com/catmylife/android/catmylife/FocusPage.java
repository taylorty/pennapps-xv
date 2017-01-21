package com.catmylife.android.catmylife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FocusPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_page);
    }

    public void giveUp(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
}
