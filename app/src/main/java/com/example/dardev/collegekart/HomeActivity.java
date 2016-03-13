package com.example.dardev.collegekart;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dardev.collegekart.Fragments.MarketFragment;
import com.example.dardev.collegekart.Fragments.MyAdsFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity  {
    private Toolbar mToolbar;
    private TextView mTextView;
    private String title;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));
        }
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent=getIntent();
        String entryType=intent.getStringExtra("EntryType");
        mTextView=(TextView)findViewById(R.id.profileName);
//        switch(entryType)
//        {
//            case "Guest"://mTextView.setText("Welcome Guest");
//                    title="GUEST";
//                    break;
//            case "Login"://mTextView.setText("Welcome");
//                    break;
//            case "SignUp"://mTextView.setText("Welcome");
//                break;
//        }
        mToolbar.setTitle(title);

       /* drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        FloatingActionButton floatingActionButton= (FloatingActionButton)findViewById(R.id.myFAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),FlightBookingActivity.class);
                startActivity(intent1);

            }
        });*/
        viewPager = (ViewPager) findViewById(R.id.flight_booking_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.flight_booking_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MarketFragment(), "Market");
        adapter.addFragment(new MyAdsFragment(),"My Ads");
        //adapter.addFragment(new GuestFragment(), "GUEST");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch(id)
      {
         case  R.id.action_settings:
             intent = new Intent(this,EditProfileActivity.class);
             startActivity(intent);
          break;
          case R.id.action_history:
              intent = new Intent(this,HistoryActivity.class);
              startActivity(intent);
      }

        return super.onOptionsItemSelected(item);
    }
    public void onAddAd(View view) {
        Intent intent =new Intent(this, NewAdActivity.class);
        startActivity(intent);
    }
}
