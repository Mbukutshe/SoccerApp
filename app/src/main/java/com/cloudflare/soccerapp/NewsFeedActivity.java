package com.cloudflare.soccerapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFeedActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    FloatingActionButton postImage;
    Toolbar toolbar;
    ViewPager viewPager;
    DrawerLayout drawer;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postImage = (FloatingActionButton)findViewById(R.id.camera);
        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsFeedActivity.this,PostsActivity.class);
                startActivity(intent); //onclick takes you to postsActivity.java

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.pictures);
        tabLayout.getTabAt(1).setIcon(R.drawable.matches);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position == 1)
                {
                    postImage = (FloatingActionButton)findViewById(R.id.camera);
                    postImage.setImageDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.plus));
                    postImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(v.getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setContentView(R.layout.activity_login);
                            RelativeLayout layout = (RelativeLayout)dialog.findViewById(R.id.login_layout);
                            final Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.layout_anim);
                            final ImageView icon = (ImageView)dialog.findViewById(R.id.icon_logo);
                            layout.startAnimation(anim);

                            Button Submit= (Button)dialog.findViewById(R.id.btnSubmit);
                            final EditText username= (EditText)dialog.findViewById(R.id.editUsername);
                            final EditText password= (EditText)dialog.findViewById(R.id.editPassword);

                            Submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                 /*   Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.loading_anim);
                                    icon.startAnimation(anim);*/
                                    final ProgressDialog myProgressDialog = new ProgressDialog(dialog.getContext());
                                    myProgressDialog.show();
                                    myProgressDialog.setContentView(R.layout.progress);
                                    ProgressBar progressBar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                                    progressBar.getIndeterminateDrawable()
                                            .setColorFilter(Color.parseColor("#d5fd00"), PorterDuff.Mode.MULTIPLY);
                                    String url = "https://soccer.payghost.co.za/login.inc.php";
                                    StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            Intent intent = new Intent(NewsFeedActivity.this,LeagueActivity.class);
                                            startActivity(intent); //onclick takes you to LeagueActivity.java
                                            dialog.dismiss();
                                            myProgressDialog.dismiss();
                                        }
                                    },new Response.ErrorListener(){
                                        @Override
                                        public void onErrorResponse(VolleyError error)
                                        {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams(){
                                            Map<String,String> parameters = new HashMap<String, String>();
                                            parameters.put("username",username.getText().toString());
                                            parameters.put("password",password.getText().toString());
                                            return parameters;
                                        }
                                    };
                                    request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(request);
                                }
                            });
                            dialog.show();

                        }
                    });
                }
                else
                    if(position == 0)
                    {
                        postImage = (FloatingActionButton)findViewById(R.id.camera);
                        postImage.setImageDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.camera));
                        postImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(NewsFeedActivity.this,PostsActivity.class);
                                startActivity(intent); //onclick takes you to postsActivity.java

                            }
                        });
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.admin_menu)
        {
            drawer.openDrawer(Gravity.RIGHT);
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if(item.getItemId()==R.id.register_team)
        {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.activity_login);
            RelativeLayout layout = (RelativeLayout)dialog.findViewById(R.id.login_layout);
            final Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.layout_anim);
            final ImageView icon = (ImageView)dialog.findViewById(R.id.icon_logo);
            layout.startAnimation(anim);

            Button Submit= (Button)dialog.findViewById(R.id.btnSubmit);
            final EditText username= (EditText)dialog.findViewById(R.id.editUsername);
            final EditText password= (EditText)dialog.findViewById(R.id.editPassword);

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.loading_anim);
                    icon.startAnimation(anim);
                    String url = "https://soccer.payghost.co.za/login.inc.php";
                    StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response)
                        {
                            Intent intent = new Intent(dialog.getContext(),RegisterTeamActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String,String> parameters = new HashMap<String, String>();
                            parameters.put("username",username.getText().toString());
                            parameters.put("password",password.getText().toString());
                            return parameters;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            });
            dialog.show();
        }
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }
    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Pictures(), "PICTURES");
        adapter.addFrag(new Matches(), "MATCHES");

        viewPager.setAdapter(adapter);
    }
    class viewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public viewPagerAdapter(FragmentManager manager) {
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
