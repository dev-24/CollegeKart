package com.example.dardev.collegekart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dardev.collegekart.R;
import com.example.dardev.collegekart.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public static final int INTENT_REQUEST_GET_IMAGES=1;
    private ImageView profileImage;

    private EditText editFirstName, editLastName, editPhone, editEmail, editPass, editPassRe;
    private RadioGroup rgEditYear, rgEditBranch;
    private RadioButton radioFe, radioSe, radioTe, radioBe, radioComp, radioIt, radioExtc, radioEtrx;


    private String sFName, sLName, sPhone, sEmail, sPass, sPassRe, sYear, sBranch;
    private Firebase ref;
    private User post;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage= (ImageView) findViewById(R.id.profile_edit_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });

        rgEditYear = (RadioGroup) findViewById(R.id.edit_year);

        rgEditBranch = (RadioGroup) findViewById(R.id.edit_branch);

        editFirstName= (EditText) findViewById(R.id.edit_firstName);
        editLastName= (EditText) findViewById(R.id.edit_lastName);
        editPhone=(EditText) findViewById(R.id.edit_input_number);
        editEmail= (EditText)findViewById(R.id.edit_input_email);
        editPass =(EditText) findViewById(R.id.edit_input_password);

        radioFe = (RadioButton) findViewById(R.id.edit_radio_fe);
        radioSe = (RadioButton) findViewById(R.id.edit_radio_se);
        radioTe = (RadioButton) findViewById(R.id.edit_radio_te);
        radioBe = (RadioButton) findViewById(R.id.edit_radio_be);
        radioComp = (RadioButton) findViewById(R.id.edit_radio_comps);
        radioIt = (RadioButton) findViewById(R.id.edit_radio_it);
        radioEtrx = (RadioButton) findViewById(R.id.edit_radio_etrx);
        radioExtc = (RadioButton) findViewById(R.id.edit_radio_extc);
        Firebase.setAndroidContext(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
        profileImage.setImageDrawable(null);

        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);

    }
    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            profileImage.setImageURI(null);
            profileImage.setImageURI(image_uris.get(0));
            //do something
        }
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


                    try {
                        byte[] imageByte = Base64.decode(post.getImage());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);
                     profileImage.setImageBitmap(bitmap);

                    } catch (Exception e) {

                    }

                    editFirstName.setText(post.getFirstname());
                    editLastName.setText(post.getLastname());
                    editEmail.setText(post.getEmail());
                    editPass.setText(post.getPassword());
                    editPhone.setText(post.getMobile());
                    switch (post.getBranch())
                    {
                        case "Comps":
                            radioComp.setChecked(true);
                            break;
                        case "EXTC":
                            radioExtc.setChecked(true);
                            break;
                        case "ETRX":
                            radioEtrx.setChecked(true);
                            break;
                        case "IT":
                            radioIt.setChecked(true);
                            break;

                    }
                    switch (post.getYear())
                    {
                        case "F.E.":
                            radioFe.setChecked(true);
                            break;
                        case "B.E.":
                            radioBe.setChecked(true);
                            break;
                        case "S.E.":
                            radioSe.setChecked(true);
                            break;
                        case "T.E.":
                            radioTe.setChecked(true);
                            break;

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



    public void onEditProfile(View view)
    {
        ref = new Firebase("https://collegekart.firebaseio.com/users/"+getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("UID",""));
        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap bitmapComp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmapComp.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();

        String image = Base64.encodeBytes(bb);
        progress = new ProgressDialog(this);

        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

    System.out.println("edit profile");




                    Map<String, Object> post1 = new HashMap<String, Object>();
                    post1.put("firstname", editFirstName.getText().toString());
                    post1.put("lastname", editLastName.getText().toString());
                    post1.put("email", editEmail.getText().toString());
                    post1.put("password", editPass.getText().toString());
                    post1.put("year", ((RadioButton) findViewById(rgEditYear.getCheckedRadioButtonId())).getText().toString());
                    post1.put("branch", ((RadioButton) findViewById(rgEditBranch.getCheckedRadioButtonId())).getText().toString());
                    post1.put("mobile", editPhone.getText().toString());
                    post1.put("image",image);

        ref.updateChildren(post1, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                                progress.hide();
                                Toast.makeText(getApplicationContext(), "Profile edited successfully", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });

            }









}
