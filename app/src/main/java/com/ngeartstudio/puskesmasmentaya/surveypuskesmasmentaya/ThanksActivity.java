package com.ngeartstudio.puskesmasmentaya.surveypuskesmasmentaya;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ThanksActivity extends AppCompatActivity {
    TextView textJam, textTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textJam =(TextView) findViewById(R.id.textJam);
        textTanggal = (TextView) findViewById(R.id.textTanggal);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));

        String localTime = date.format(currentLocalTime);

        textTanggal.setText(formattedDate);
        textJam.setText(localTime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(ThanksActivity.this, SurveyActivity.class);
                ThanksActivity.this.startActivity(mainIntent);
                ThanksActivity.this.finish();
            }
        }, 5000);
    }
}
