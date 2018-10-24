package nontachai.becomedev.wallpaperapp.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import nontachai.becomedev.wallpaperapp.R;
import nontachai.becomedev.wallpaperapp.fragment.FavouriteFragment;
import nontachai.becomedev.wallpaperapp.fragment.HomeFragment;
import nontachai.becomedev.wallpaperapp.fragment.SettingFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
//    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        MobileAds.initialize(this,"ca-app-pub-1787292132881960~8499100450");//ca-app-pub-3940256099942544~3347511713





        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());
    }

    private void displayFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_area,fragment).commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        switch (item.getItemId())
        {
            case R.id.nav_home : fragment = new HomeFragment();break;
            case R.id.nav_fav : fragment = new FavouriteFragment();break;
            case R.id.nav_setting : fragment = new SettingFragment();break;
            default: fragment = new HomeFragment(); break;
        }
        displayFragment(fragment);
        return true;
    }
}
