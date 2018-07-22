package becomedev.nontachai.wallpaperapp.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import becomedev.nontachai.wallpaperapp.R;
import becomedev.nontachai.wallpaperapp.fragment.FavouriteFragment;
import becomedev.nontachai.wallpaperapp.fragment.HomeFragment;
import becomedev.nontachai.wallpaperapp.fragment.SettingFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");//ca-app-pub-3940256099942544~3347511713

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
