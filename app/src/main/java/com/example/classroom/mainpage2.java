package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class mainpage2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button mainreq, addstu, exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage2);


        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);

        toolbar=findViewById(R.id.toolbar);

//        super.setContentView(drawerLayout);

        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        mainreq = (Button) findViewById(R.id.button3);
        addstu = (Button) findViewById(R.id.button10);
        exit = (Button) findViewById(R.id.button11);





        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
      navigationView.setCheckedItem(R.id.nav_home);

        mainreq.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                startActivity(new Intent(mainpage2.this, mainpage.class));
                finish();

            }

        });
        addstu.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){
                startActivity(new Intent(mainpage2.this, student.class));
                finish();

            }

        });
        exit.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){

                finish();

            }

        });

}
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String uname=getIntent().getStringExtra("Username");
        switch (menuItem.getItemId()) {
            case R.id.nav_home: break;

            case R.id.nav_login_menu:
                Intent intent1 = new Intent(mainpage2.this, adminlogin.class);
                intent1.putExtra("Username",uname);
                startActivity(intent1);
                break;
            case R.id.nav_logout_menu:
//                Log.e("Username",uname);
                Intent intent2 = new Intent(mainpage2.this, admins.class);
                startActivity(intent2);
                break;
            case R.id.nav_share_menu:
               share_app();
                break;
            case R.id.nav_rate_menu:
              RateTheApp();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    public void share_app() {

            final String appPackageName = getPackageName();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            String postData = "Get CampusCare app click on the link and download now" + ":https://play.google.com/store/apps/details?id=" + appPackageName;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Now!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, postData);
            shareIntent.setType("text/Plain");
            startActivity(Intent.createChooser(shareIntent, "Share post via"));

        }
        private void RateTheApp(){
        final String appPackageName=getPackageName();
        try {
             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName)));
        }
        catch(android.content.ActivityNotFoundException anfe){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));

            }
        }


   }
