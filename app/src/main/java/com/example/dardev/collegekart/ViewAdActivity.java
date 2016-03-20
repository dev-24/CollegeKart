package com.example.dardev.collegekart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dardev.collegekart.R;
import com.example.dardev.collegekart.model.Ad;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;

import java.util.HashMap;
import java.util.Map;

public class ViewAdActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Intent intent;
    private Firebase ref;
    private TextView prodName, prodDescription, prodPrice, prodRentPeriod;
    private Button buyrent, viewProfile;
    private ImageView viewAdImage;
   private SharedPreferences sharedPreferences;
  private  Ad post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("AdDetail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent=getIntent();
        Firebase.setAndroidContext(this);

        prodName = (TextView)findViewById(R.id.view_product_name);
        prodDescription = (TextView)findViewById(R.id.view_product_description);
        prodPrice = (TextView)findViewById(R.id.view_product_price);
        prodRentPeriod = (TextView)findViewById(R.id.view_rent_period);

        buyrent = (Button)findViewById(R.id.btn_buy_rent);
        viewProfile = (Button)findViewById(R.id.btn_view_profile);

        viewAdImage = (ImageView) findViewById(R.id.profile_image);
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);

        buyrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress = new ProgressDialog(ViewAdActivity.this);

                progress.setMessage("Loading");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
                ref = new Firebase("https://collegekart.firebaseio.com/ads/"+ intent.getStringExtra("key")+"/buyrequests");
                Map<String, Object> post1 = new HashMap<String, Object>();
                post1.put(sharedPreferences.getString("UID", ""), "true");
                ref.updateChildren(post1, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        Firebase fireRef= new Firebase("https://collegekart.firebaseio.com/users/"+sharedPreferences.getString("UID","")+"/buyrequests");
                        Map<String, Object> buyRequest = new HashMap<String, Object>();
                        buyRequest.put(intent.getStringExtra("key"),"true");
                        fireRef.updateChildren(buyRequest, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                progress.hide();
                                Toast.makeText(getApplicationContext(), "Request created successfully!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UserProfileActivity.class);
                intent.putExtra("key",post.getSeller());
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_ad, menu);
        return true;
    }

    @Override
    protected void onResume() {
        ref = new Firebase("https://collegekart.firebaseio.com/ads/"+intent.getStringExtra("key"));
        System.out.println(intent.getStringExtra("key"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    post = dataSnapshot.getValue(Ad.class);
                    if (post.getType().equals("RENT")) prodRentPeriod.setText(post.getPeriod() + " Months");
                    else  prodRentPeriod.setText("");
                    prodPrice.setText("INR " + post.getPrice());
                    prodDescription.setText("Description: "+post.getDesc());
                    prodName.setText("Product: "+post.getTitle());
                    try {
                        byte[] imageByte = Base64.decode(post.getImage());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);
                        viewAdImage.setImageBitmap(bitmap);

                    } catch (Exception e) {

                    }

                    System.out.println(post.getTitle());

                }

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            // ....
        });

        super.onResume();
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

}
