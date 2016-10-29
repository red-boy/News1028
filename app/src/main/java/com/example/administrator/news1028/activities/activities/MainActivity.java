package com.example.administrator.news1028.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.entity.NewsTypes;
import com.example.administrator.news1028.activities.fragment.FavorFragment;
import com.example.administrator.news1028.activities.fragment.HomeFragment;
import com.example.administrator.news1028.activities.fragment.HotFragment;
import com.example.administrator.news1028.activities.fragment.NewsListFragment;
import com.example.administrator.news1028.activities.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,NewsListFragment.OnFragmentInteractionListener {

    public static final String KEY_TYPELIST ="key_List" ;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    HomeFragment mHomeFragment;
    HotFragment mHotFragment;
    FavorFragment mFavorFragment;
    SettingFragment mSettingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFavorFragment = new FavorFragment();
        mHomeFragment = HomeFragment.newInstance(getIntent().getExtras());
        mSettingFragment = new SettingFragment();
        mHotFragment = new HotFragment();
        replaceFragment(R.id.main_content, mHomeFragment);//默认是第一个碎片

        mRadioGroup.setOnCheckedChangeListener(this);

    }

    public void replaceFragment(int viewId, Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(viewId, fragment, fragment.getClass().getSimpleName());
        ft.commit();

    }

    public static void start(Context context, NewsTypes newsTypes) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra(KEY_TYPELIST,newsTypes); //对象(集合)必须可序列化
        context.startActivity(starter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                replaceFragment(R.id.main_content, mHomeFragment);
                break;
            case R.id.radioButton2:
                replaceFragment(R.id.main_content, mHotFragment);
                break;
            case R.id.radioButton3:
                replaceFragment(R.id.main_content, mFavorFragment);
                break;
            case R.id.radioButton4:
                replaceFragment(R.id.main_content,mSettingFragment);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
