package com.example.dardev.collegekart;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dardev.collegekart.Fragments.LoginFragment;
import com.example.dardev.collegekart.Fragments.SignUpFragment;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity { /* When using Appcombat support library
                                                         you need to extend Main Activity to
                                                         ActionBarActivity.
                                                      */

    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Firebase ref;

    /*Login*/
    private EditText loginEmail, loginPassword;
    private String logEmail, logPass;

    /*Password*/
    private RadioGroup rgYear, rgBranch;
    private EditText firstName, lastName, mobile, email, password, passwordRe;
    private String sFName, sLName, sMob, sEm, sPass, sPassRe, sYear, sBranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref = new Firebase("https://collegekart.firebaseio.com/users");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));
        }
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        /*Login*/
        loginEmail = (EditText) findViewById(R.id.input_email);
        loginPassword = (EditText) findViewById(R.id.input_password);

        /*SignUp*/
        rgYear = (RadioGroup) findViewById(R.id.rg_year);
        String year = ((RadioButton)findViewById(rgYear.getCheckedRadioButtonId() )).getText().toString();

        rgBranch = (RadioGroup) findViewById(R.id.rg_branch);
        String branch = ((RadioButton)findViewById(rgBranch.getCheckedRadioButtonId())).getText().toString();

        firstName= (EditText) findViewById(R.id.input_firstName);
        lastName= (EditText) findViewById(R.id.input_lastName);
        mobile=(EditText) findViewById(R.id.input_number);
        email= (EditText)findViewById(R.id.input_email);
        password =(EditText) findViewById(R.id.input_password);
        passwordRe= (EditText)findViewById(R.id.input_password_reenter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "LOGIN");
        adapter.addFragment(new SignUpFragment(), "SIGN UP");
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
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    public String isLoginValid()
    {

        logPass = loginPassword.getText().toString().trim();
        logEmail = loginEmail.getText().toString().trim();


        if( logPass.equals("") && validateEmail(logEmail))
        {
            return "Login Password";
        }
        if( logPass.equals("") && logPass.length() > 6)
        {
            return "Signup Password";
        }

        return "Login Valid";

    }

    public String isSignUpValid()
    {

        sFName= firstName.getText().toString().trim();
        sLName = lastName.getText().toString().trim();
        sMob = mobile.getText().toString().trim();
        sEm = email.getText().toString().trim();
        sPass = password.getText().toString().trim();
        sPassRe = passwordRe.getText().toString().trim();

        sYear = ((RadioButton)findViewById(rgYear.getCheckedRadioButtonId() )).getText().toString();
        sBranch = ((RadioButton)findViewById(rgBranch.getCheckedRadioButtonId())).getText().toString();

        if( sFName.equals("") )
        {
            return "First Name";
        }
        if( sLName.equals("") )
        {
            return "Last Name";
        }
        if( sMob.equals(""))
        {
            return "Mobile";
        }
        if( sEm.equals("") && validateEmail(sEm))
        {
            return "Email";
        }
        if( sPass.equals("") && sPass.length() > 6 )
        {
            return "Password";
        }
        if( sPassRe.equals("") && sPassRe.equals(sPass))
        {
            return "Re-enter Password";
        }
        if( sYear.equals("") )
        {
            return "Year";
        }
        if( sBranch.equals("") )
        {
            return "Branch";
        }

        return "SignUp Valid";
    }






    private boolean validateEmail(String email) {

        return false;
    }
}

