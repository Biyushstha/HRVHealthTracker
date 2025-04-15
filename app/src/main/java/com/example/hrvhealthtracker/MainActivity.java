package com.example.hrvhealthtracker;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hrvhealthtracker.sync.WaterRequestReceiver;
import com.example.hrvhealthtracker.sync.WaterSyncService;
import com.example.hrvhealthtracker.ui.ActionsFragment;
import com.example.hrvhealthtracker.ui.HomeFragment;
import com.google.android.gms.wearable.Wearable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;

    private ActionsFragment actionsFragment;
    private HomeFragment homeFragment;


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "📩 Registering WaterRequestReceiver");
        Wearable.getMessageClient(this).addListener(new WaterRequestReceiver());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottom_nav);

        // ✅ Instantiate fragments
        homeFragment = new HomeFragment();
        actionsFragment = new ActionsFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(actionsFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (id == R.id.nav_actions) {
                viewPager.setCurrentItem(1);
                return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNav.getMenu().getItem(position).setChecked(true);
            }
        });
    }
}
