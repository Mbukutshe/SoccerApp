package com.cloudflare.soccerapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button Register, NewsFeed; //Declaring and importing
    ImageButton postImage;
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView)findViewById(R.id.splash_logo);
        final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        upAnim.reset();
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.splash_layout);
        layout.clearAnimation();
        layout.setAnimation(upAnim);

        upAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        TextView appName = (TextView)findViewById(R.id.app_name);
        appName.clearAnimation();
        appName.startAnimation(upAnim);
        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        imageView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setAnimation(fadeOut);
                Intent mainIntent = new Intent(getApplicationContext(),NewsFeedActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
