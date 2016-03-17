package com.example.dardev.collegekart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dardev.collegekart.model.Ad;
import com.example.dardev.collegekart.model.BuyRequest;
import com.example.dardev.collegekart.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BuyRequestsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView setting_list;
    private ArrayList<User> options;
    private OptionsAdapter optionsAdapter;
    private Toolbar mToolbar;
    private SharedPreferences sharedPreferences;
    private Firebase ref;
    private User post;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_requests);
        setting_list = (ListView) findViewById(R.id.list_buy_requests);

        options = new ArrayList<User>();


        optionsAdapter = new OptionsAdapter();
        setting_list.setAdapter(optionsAdapter);
        setting_list.setOnItemClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("Buy Request By");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Firebase.setAndroidContext(this);
        sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User advertClicked = (User)optionsAdapter.getItem(i);

        Intent intent = new Intent(this,UserProfileActivity.class);
        intent.putExtra("key",advertClicked.getKey());
        startActivity(intent);
    }

    class Holder{
        TextView title;

        ImageView icon;
    }

    class OptionsAdapter extends BaseAdapter
    {
        public OptionsAdapter() {
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if(convertView==null)
            {
                LayoutInflater li =  getLayoutInflater();
                convertView = li.inflate(R.layout.buy_request_list_item, null);
                holder=new Holder();
                holder.title = (TextView) convertView.findViewById(R.id.txt_titel);
                holder.icon = (ImageView) convertView.findViewById(R.id.optionIcon);
                convertView.setTag(holder);
            }


            else
            {
                holder=(Holder) convertView.getTag();
            }

            User item = (User) getItem(position);
            holder.title.setText(item.getFirstname().toUpperCase() + " " + item.getLastname().toUpperCase());
            System.out.println(holder.title.getText());
            if(item.getImage()!=null) {
                try {
                    byte[] imageByte = Base64.decode(item.getImage());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);
                    holder.icon.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FloatingActionButton fab = (FloatingActionButton) convertView.findViewById(R.id.fab);
            fab.setTag(item.getKey());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   final String key= (String) view.getTag();
                    ref = new Firebase("https://fiery-inferno-2210.firebaseio.com/ads/"+getIntent().getStringExtra("key"));


                    ref.addListenerForSingleValueEvent(new ValueEventListener() {



                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Map<String, String> post1 = new HashMap<String, String>();
                                post1.put("product", dataSnapshot.child("title").getValue(String.class));
                                String period;
                                post1.put("period",  period = dataSnapshot.child("period") == null ? "" : dataSnapshot.child("period").getValue(String.class));
                                post1.put("seller", sharedPreferences.getString("UID",""));
                                post1.put("buyer", key);
                                post1.put("type", dataSnapshot.child("type").getValue(String.class));
                                post1.put("image", dataSnapshot.child("image").getValue(String.class));
                                post1.put("time", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+(1+Calendar.getInstance().get(Calendar.MONTH))+"/"+Calendar.getInstance().get(Calendar.YEAR));
                                Firebase newRef = new Firebase("https://fiery-inferno-2210.firebaseio.com/transactions/"+getIntent().getStringExtra("key"));
                                newRef.setValue(post1, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                        if (firebaseError != null) {
                                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                                        } else {
                                            ref.setValue(null);
                                            Toast.makeText(getApplicationContext(), "Buyer approved!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), ViewAdActivity.class);
                                            intent.putExtra("key", key);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }






                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                        // ....
                    });

                }
            });


            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy_requests, menu);
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
        progress = new ProgressDialog(this);

        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        ref = new Firebase("https://fiery-inferno-2210.firebaseio.com/ads/"+intent.getStringExtra("key"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                        System.out.println("ad "+dataSnapshot.getKey());

                        DataSnapshot buyRequests=  dataSnapshot.child("buyrequests");
                        if (buyRequests != null) {
                            for(DataSnapshot brChild: buyRequests.getChildren())
                            {
                                System.out.println("buyrequest "+brChild.getKey());
                                Firebase fireref = new Firebase("https://fiery-inferno-2210.firebaseio.com/users/"+brChild.getKey());
                                fireref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            progress.hide();


                                                post = dataSnapshot.getValue(User.class);
                                                post.setKey(dataSnapshot.getKey());
                                                options.add(post);



                                        }
                                        optionsAdapter = new OptionsAdapter();


                                        setting_list.setAdapter(optionsAdapter);

                                        optionsAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });


                            }
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
