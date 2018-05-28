package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.jokepresenterandroidlib.JokePresenterActivity;


public class MainActivity extends AppCompatActivity {

    public static MutableLiveData<String> mJoke;
    private Context mContext;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        mContext = this;
        final Activity activity = this;
        mJoke = new MutableLiveData<String>();
        mJoke.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                String message = mJoke.getValue();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(activity, JokePresenterActivity.class);
                intent.putExtra(JokePresenterActivity.KEY_JOKE, message);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        Context context = getApplicationContext();
        runAsync();


    }

    private void runAsync() {
        progressBar.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Dummy"));
    }

}
