package com.example.dardev.collegekart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dardev.collegekart.model.Ad;
import com.example.dardev.collegekart.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView image;
    private Firebase ref;
    private User post;
    private TextView prof_name,prof_year,prof_branch, prof_email, prof_mobile;
    private ImageView prof_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Firebase.setAndroidContext(this);
        prof_branch= (TextView) findViewById(R.id.profile_branch);
        prof_email= (TextView) findViewById(R.id.profile_email);
        prof_name= (TextView) findViewById(R.id.profile_name);
        prof_image= (ImageView) findViewById(R.id.profile_image);
        prof_mobile= (TextView) findViewById(R.id.profile_contact);
        prof_year= (TextView) findViewById(R.id.profile_year);


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

    @Override
    protected void onResume() {
        Intent intent=getIntent();
        ref = new Firebase("https://collegekart.firebaseio.com/users/"+intent.getStringExtra("key"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {


                    post = dataSnapshot.getValue(User.class);
                    prof_name.setText(post.getFirstname().toUpperCase()+" "+ post.getLastname().toUpperCase());
                    prof_mobile.setText("Contact: "+post.getMobile());
                    prof_year.setText("Year: "+post.getYear());
                    prof_email.setText(post.getEmail());
                    prof_branch.setText("Class: "+post.getBranch().toUpperCase());

                    try {
                        byte[] imageByte = Base64.decode(post.getImage());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);
                    prof_image.setImageBitmap(bitmap);

                    } catch (Exception e) {

                    }


                }

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            // ....
        });
        super.onResume();
    }
}
