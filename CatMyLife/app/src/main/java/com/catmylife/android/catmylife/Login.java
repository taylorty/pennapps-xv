package com.catmylife.android.catmylife;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    public static EditText t;
    private static String name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t = (EditText) findViewById(R.id.loginName);
        ImageView normalCat = (ImageView) findViewById(R.id.cat);
        AnimationDrawable normalCatAnimation =    (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }

    public static String getName() {
        return name;
    }

    public void submit(View view) {
        name = t.getText().toString();
        System.out.println(name);
        if (name.matches("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }


    }
}
