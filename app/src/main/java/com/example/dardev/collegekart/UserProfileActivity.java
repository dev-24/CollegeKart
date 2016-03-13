package com.example.dardev.collegekart;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gun0912.tedpicker.ImagePickerActivity;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final int INTENT_REQUEST_GET_IMAGES = 1;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image= (ImageView) findViewById(R.id.profile_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getImages() {

        Intent intent  = new Intent(this, ImagePickerActivity.class);
        startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);

    }
    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            //do something
        }
    }

}
