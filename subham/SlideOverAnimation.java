package com.github.florent37.materialviewpager.subham;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



/**
 * Created by rohit on 22/7/15.
 */
public class SlideOverAnimation extends BaseAppIntro {
//http://resolvefinance.co.uk/Whatsapp/login.php
    @Override
    public void init(Bundle savedInstanceState) {

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }

        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));

        setSlideOverAnimation();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
