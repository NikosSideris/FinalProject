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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {

    public static MutableLiveData<String> mJoke;
    private Context mContext;
    private ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

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

        if (BuildConfig.FLAVOR.equals("free")) {
            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        }
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
        if (BuildConfig.FLAVOR.equals("free")) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    mInterstitialAd.show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    runAsync();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.

                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the interstitial ad is closed.
                    runAsync();
                }
            });

        } else {
            runAsync();
        }

    }

    private void runAsync() {
        progressBar.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Dummy"));
    }

}
