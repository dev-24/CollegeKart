package com.example.dardev.collegekart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewActivity extends Activity {

    TextView personName;
    TextView personAge;
    ImageView personPhoto;
    TextView abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_activity);
        personName = (TextView)findViewById(R.id.flight_cost);
        personAge = (TextView)findViewById(R.id.flight_time);
        abc=(TextView)findViewById(R.id.flight_duration);
        personPhoto = (ImageView)findViewById(R.id.plane_picture);
    }
}
