package com.example.dardev.collegekart;

import android.content.Intent;
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

import com.example.dardev.collegekart.model.Ad;
import com.example.dardev.collegekart.model.BuyRequest;

import java.util.ArrayList;

public class BuyRequestsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView setting_list;
    private ArrayList<BuyRequest> options;
    private OptionsAdapter optionsAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_requests);
        setting_list = (ListView) findViewById(R.id.list_buy_requests);

        options = new ArrayList<BuyRequest>();
        options.add(new BuyRequest("Login/Logout", R.drawable.ic_profile));
        options.add(new BuyRequest("Login/Logout" ,R.drawable.ic_profile));
        options.add(new BuyRequest("Login/Logout" ,R.drawable.ic_profile));
        options.add(new BuyRequest("Login/Logout" ,R.drawable.ic_profile));

        optionsAdapter = new OptionsAdapter();
        setting_list.setAdapter(optionsAdapter);
        setting_list.setOnItemClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(mToolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        mToolbar.setTitle("Buy Request By");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BuyRequest advertClicked = (BuyRequest)optionsAdapter.getItem(i);

        Intent intent = new Intent(this,BuyRequestsActivity.class);
//based on item add info to intent
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
                LayoutInflater li =  LayoutInflater.from(getApplicationContext());
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

            BuyRequest item = (BuyRequest) getItem(position);
            holder.title.setText(item.getTitle());
            System.out.println(holder.title.getText());
            holder.icon.setImageResource(item.getImageId());

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

}
