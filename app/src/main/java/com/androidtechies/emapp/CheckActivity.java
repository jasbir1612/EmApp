package com.androidtechies.emapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

/**<p>
 * Created by Angad on 25/03/2016.
 * </p>
 */
public class CheckActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        AppCompatButton btn = (AppCompatButton) findViewById(R.id.button);
        long time = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour <= 6)
        {   btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        }
        else
        {   startActivity(new Intent(CheckActivity.this,MainActivity.class));
            finish();
        }
    }
}
