package com.example.android.jokepresenterandroidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class JokePresenterActivity extends AppCompatActivity {

    public static final String KEY_JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);

        TextView presenter = findViewById(R.id.tv_presenter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String temp = bundle.getString(KEY_JOKE) + " to jokepresenterandroidlib";
            presenter.setText(temp);
        }
    }
}
