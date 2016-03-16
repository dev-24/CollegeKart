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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dardev.collegekart.R;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public static final int INTENT_REQUEST_GET_IMAGES=1;
    private ImageView profileImage;

    private EditText editFirstName, editLastName, editPhone, editEmail, editPass, editPassRe;
    private RadioGroup rgEditYear, rgEditBranch;

    private String sFName, sLName, sPhone, sEmail, sPass, sPassRe, sYear, sBranch;

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

        rgEditYear = (RadioGroup) findViewById(R.id.rg_year);
        String sYear = ((RadioButton)findViewById(rgEditYear.getCheckedRadioButtonId() )).getText().toString();

        rgEditBranch = (RadioGroup) findViewById(R.id.rg_year);
        String sBranch = ((RadioButton)findViewById(rgEditBranch.getCheckedRadioButtonId() )).getText().toString();

        editFirstName= (EditText) findViewById(R.id.edit_firstName);
        editLastName= (EditText) findViewById(R.id.edit_lastName);
        editPhone=(EditText) findViewById(R.id.edit_input_number);
        editEmail= (EditText)findViewById(R.id.edit_input_email);
        editPass =(EditText) findViewById(R.id.edit_input_password);
        editPassRe= (EditText)findViewById(R.id.edit_input_password_reenter);

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

    public String isProfileValid()
    {

        sFName= editFirstName.getText().toString().trim();
        sLName = editLastName.getText().toString().trim();
        sPhone = editPhone.getText().toString().trim();
        sEmail = editEmail.getText().toString().trim();
        sPass = editPass.getText().toString().trim();
        sPassRe = editPassRe.getText().toString().trim();

        sYear = ((RadioButton)findViewById(rgEditYear.getCheckedRadioButtonId() )).getText().toString();
        sBranch = ((RadioButton)findViewById(rgEditBranch.getCheckedRadioButtonId())).getText().toString();

        if( sFName.equals("") )
        {
            return "Profile First Name";
        }
        if( sLName.equals("") )
        {
            return "Profile Last Name";
        }
        if( sPhone.equals(""))
        {
            return "Profile Mobile";
        }
        if( sEmail.equals("") && validateEmail(sEmail))
        {
            return "Profile Email";
        }
        if( sPass.equals("") && sPass.length() > 6 )
        {
            return "Profile Password";
        }
        if( sPassRe.equals("") && sPassRe.equals(sPass))
        {
            return "Profile Re-enter Password";
        }
        if( sYear.equals("") )
        {
            return "Profile Year";
        }
        if( sBranch.equals("") )
        {
            return "Profile Branch";
        }

        return "Profile Valid";
    }

    private boolean validateEmail(String email) {

        return false;
    }

}
