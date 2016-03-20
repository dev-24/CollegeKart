package com.example.dardev.collegekart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.utilities.Base64;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewAdActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public static final int INTENT_REQUEST_GET_IMAGES=1;
    private ImageView newAdImage;

    private RadioGroup rgSellRent;
    private EditText productName, productPrice, productDescription, productRentPeriod;
    private TextInputLayout productRentPeriodLayout;
    private String pName, pPrice, pDesc, pRentPeriod, pSellRent;
    SharedPreferences sharedPreferences;
    private Firebase ref;
    private Firebase newRef;
    private Firebase fireref;
    private ProgressDialog progress;

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
        productRentPeriod = (EditText)findViewById(R.id.edit_new_period);

        productRentPeriodLayout = (TextInputLayout) findViewById(R.id.input_layout_new_period);
        productRentPeriodLayout.setVisibility(View.INVISIBLE);

        rgSellRent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton_rent)
                {
                    productRentPeriodLayout.setVisibility(View.VISIBLE);
                }
                else if(checkedId==R.id.radioButton_sell)
                {
                    productRentPeriodLayout.setVisibility(View.INVISIBLE);
                    productRentPeriod.setText("0");
                }
            }
        });
        productName = (EditText) findViewById(R.id.edit_new_product_name);
        productPrice = (EditText) findViewById(R.id.edit_new_price);
        productDescription =(EditText) findViewById(R.id.edit_new_description);
        Firebase.setAndroidContext(this);
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);

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

    public void onCreateNewAd(View view)
    {
        progress = new ProgressDialog(this);

        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        BitmapDrawable drawable = (BitmapDrawable) newAdImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap bitmapComp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmapComp.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();

        String image = Base64.encodeBytes(bb);

        Map<String, String> post1 = new HashMap<String, String>();
        post1.put("title", productName.getText().toString());
        post1.put("desc", productDescription.getText().toString());
        post1.put("price", productPrice.getText().toString());
        post1.put("image", image);
        post1.put("period", productRentPeriod.getText().toString());
        post1.put("seller", sharedPreferences.getString("UID", ""));
        post1.put("type", ((RadioButton)findViewById(rgSellRent.getCheckedRadioButtonId())).getText().toString());
        fireref = new Firebase("https://fiery-inferno-2210.firebaseio.com/ads");

        newRef= fireref.push();
        newRef.setValue(post1, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");

                    String postId = newRef.getKey();
                    ref= new Firebase("https://fiery-inferno-2210.firebaseio.com/users/"+sharedPreferences.getString("UID","")+"/ads");
                    Map<String, Object> ads = new HashMap<String, Object>();
                    ads.put(postId,"true");
                    ref.updateChildren(ads, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            progress.hide();
                            Toast.makeText(getApplicationContext(),"Advertisement created successfully!",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });



                }
            }
        });

    }



  /*  public String isValid(){

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
*/



}
