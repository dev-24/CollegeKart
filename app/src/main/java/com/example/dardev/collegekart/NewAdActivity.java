package com.example.dardev.collegekart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NewAdActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public static final int INTENT_REQUEST_GET_IMAGES=1;
    private ImageView newAdImage;

    private RadioGroup rgSellRent;
    private EditText productName, productPrice, productDescription, productRentPeriod;

    private String pName, pPrice, pDesc, pRentPeriod, pSellRent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        mToolbar.setTitle("New Advertisement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newAdImage= (ImageView) findViewById(R.id.new_ad_image);
        newAdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });

        rgSellRent = (RadioGroup) findViewById(R.id.edit_new_sell_rent);
        productName = (EditText) findViewById(R.id.input_firstName);
        productPrice = (EditText) findViewById(R.id.input_lastName);
        productDescription =(EditText) findViewById(R.id.input_number);
        productRentPeriod = (EditText)findViewById(R.id.input_email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_ad, menu);

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
            newAdImage.setImageURI(null);
            newAdImage.setImageURI(image_uris.get(0));


            //do something
        }
    }

    public String isValid(){

        pName= productName.getText().toString().trim();
        pPrice = productPrice.getText().toString().trim();
        pDesc = productDescription.getText().toString().trim();
        pRentPeriod = productRentPeriod.getText().toString().trim();
        pSellRent = ((RadioButton)findViewById(rgSellRent.getCheckedRadioButtonId() )).getText().toString();

        if( pName.equals("") )
        {
            return "Product Name";
        }
        if( pPrice.equals("") )
        {
            return "Product Price";
        }
        if( pDesc.equals("") )
        {
            return "Product Desription";
        }
        if( pRentPeriod.equals("") )
        {
            return "Product Period";
        }
        if( pSellRent.equals("") )
        {
            return "Product Sell/Rent";
        }


        return "New Ad. Valid";
    }




}
