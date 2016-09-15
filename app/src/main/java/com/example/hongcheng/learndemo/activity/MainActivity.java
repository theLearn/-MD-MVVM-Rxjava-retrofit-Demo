package com.example.hongcheng.learndemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.adapter.FragmentAdapter;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.fragment.SmartCardsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;

    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initViewModel() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);

        NavigationView nv = (NavigationView) findViewById(R.id.nv_main);
        nv.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabs_main);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());

        titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.smart_cards));
        titles.add(getResources().getString(R.string.smart_devices));
        titles.add(getResources().getString(R.string.smart_sences));
        titles.add(getResources().getString(R.string.smart_controllers));
        titles.add(getResources().getString(R.string.smart_find));

        fragments = new ArrayList<Fragment>();
        fragments.add(new SmartCardsFragment());
        fragments.add(new SmartCardsFragment());
        fragments.add(new SmartCardsFragment());
        fragments.add(new SmartCardsFragment());
        fragments.add(new SmartCardsFragment());

        nv.setCheckedItem(0);
        setAbTitle(R.string.home);
        changeShows(titles, fragments);
    }

    private void changeShows(String title, Fragment fragment) {
        List<String> tempTitles = new ArrayList<String>();
        List<Fragment> tempFragments = new ArrayList<Fragment>();
        tempTitles.add(title);
        tempFragments.add(fragment);
        changeShows(tempTitles, tempFragments);
    }

    private void changeShows(List<String> titles, List<Fragment> fragments) {

        if (titles == null || fragments == null) {
            return;
        }

        mViewPager.removeAllViews();
        mTabLayout.removeAllTabs();
        for (String title : titles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }

        mAdapter.setData(fragments, titles);
        mViewPager.setAdapter(mAdapter);

        if (titles.size() <= 1) {
            mTabLayout.setVisibility(View.GONE);
        } else {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.isChecked()) {
            return false;
        }

        switch (item.getItemId()) {
            case R.id.nav_main_home:
                item.setChecked(true);
                setAbTitle(R.string.home);
                changeShows(titles, fragments);
                break;
            case R.id.nav_main_extent:
                item.setChecked(true);
                setAbTitle(R.string.my_apps);
                break;
            case R.id.nav_main_market:
                item.setChecked(true);
                setAbTitle(R.string.my_shopping);
                break;
            case R.id.nav_main_message:
                item.setChecked(true);
                setAbTitle(R.string.my_message);
                break;
            case R.id.nav_main_search:
                startActivity(new Intent(this, FindActivity.class));
                return false;
            case R.id.nav_main_setting:
                item.setChecked(true);
                setAbTitle(R.string.setting);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tb_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_message:
                break;
            case R.id.action_fav:
                break;
            case R.id.action_search:
                break;
            default:
                break;
        }
        return true;
    }
}