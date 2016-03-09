package com.androidtechies.emapp;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.androidtechies.fragments.EnteredFragment;
import com.androidtechies.fragments.LeftFragment;
import com.androidtechies.fragments.ScanFragment;
import com.androidtechies.model.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager)
    {   ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String titles[]=getResources().getStringArray(R.array.tab_titles);
        adapter.addFrag(new ScanFragment(),titles[0]);
        adapter.addFrag(new EnteredFragment(),titles[1]);
        adapter.addFrag(new LeftFragment(),titles[2]);
        viewPager.setAdapter(adapter);
    }
}
