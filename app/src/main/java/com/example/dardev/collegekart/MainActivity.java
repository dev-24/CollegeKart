package com.example.dardev.collegekart;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.dardev.collegekart.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static Firebase ref; /* When using Appcombat support library
                                                         you need to extend Main Activity to
                                                         ActionBarActivity.
                                                      */
    private static Firebase newRef;

    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private TabLayout tabLayout;
    private ViewPager viewPager;

    /*Login*/


    /*Password*/
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        sharedPreferences= getSharedPreferences("MyPrefs",MODE_PRIVATE);

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
       if( !sharedPreferences.getString("UID","").equals(""))
       {
           Intent intent=new Intent(getApplicationContext(),HomeActivity.class);

           startActivity(intent);
           finish();


       }



        /*SignUp*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "LOGIN");
        adapter.addFragment(new SignUpFragment(), "SIGN UP");
        //adapter.addFragment(new GuestFragment(), "GUEST");
        viewPager.setAdapter(adapter);
    }

    public void login(final EditText email, final EditText password) {
        ref = new Firebase("https://fiery-inferno-2210.firebaseio.com/users");

        Query queryRef =ref.orderByChild("email").equalTo(email.getText().toString());

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    email.setError("No such user");

                } else {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        User post = child.getValue(User.class);
                        if (post.getPassword().equals(password.getText().toString())) {
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("UID",child.getKey());
                            editor.commit();
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);

                            startActivity(intent);
                            finish();

                        }
                    }
                }

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            // ....
        });


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

    public void register(final String fName, final String lName, final String email, final String password, final String year, final String branch, final String mobile)
    {        ref = new Firebase("https://fiery-inferno-2210.firebaseio.com/users");

        Query queryRef =ref.orderByChild("email").equalTo(email);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    Map<String, String> post1 = new HashMap<String, String>();
                    post1.put("firstname", fName);
                    post1.put("lastname", lName);
                    post1.put("email", email);
                    post1.put("password", password);
                    post1.put("year", year);
                    post1.put("branch", branch);
                    post1.put("mobile", mobile);
                    newRef= ref.push();
                    newRef.setValue(post1, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");

                                String postId = newRef.getKey();
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("UID", postId);
                                editor.commit();
                                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                System.out.println(postId);

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


    /*public String isLoginValid()
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

    }*/

   /* public String isSignUpValid()
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
    }*/






    /*private boolean validateEmail(String email) {

        return false;
    }*/
}

