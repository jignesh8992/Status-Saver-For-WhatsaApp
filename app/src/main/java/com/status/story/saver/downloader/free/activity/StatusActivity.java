package com.status.story.saver.downloader.free.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.jignesh.jndroid.utils.Utils;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.ads.BannerAdsHelper;
import com.status.story.saver.downloader.free.fragment.PhotoFragment;
import com.status.story.saver.downloader.free.fragment.VideoFragment;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends BaseActivity {

    private String TAG = StatusActivity.class.getSimpleName();

    // Views
    private LinearLayout linearToolbar;
    //  private View statusBar;
    private RelativeLayout realtiveToolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TabLayout tabs;
    private ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(StatusActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mContext = StatusActivity.this;
    }

    @Override
    protected Activity getContext() {
        return StatusActivity.this;
    }

    @Override
    public void initViews() {

        linearToolbar = findViewById(R.id.linear_toolbar);
        //  statusBar = findViewById(R.id.status_bar);
        realtiveToolbar = findViewById(R.id.realtive_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tabs = findViewById(R.id.tabs);
        viewpager = findViewById(R.id.viewpager);


    }

    @Override
    public void initAds() {
        if (new AdsManager(mContext).isNeedToShowAds()) {
            try {
                BannerAdsHelper.getInstance().loadBanner(mContext);
            } catch (Exception e) {
                Log.e(TAG_ADS, "Ads_Banner_Load_Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        tvTitle.setTypeface(typeface);
    }

    @Override
    public void initData() {
        if (Constant.TYPE_STATUS == Constant.TYPE_WHATSAPP_STATUS) {
            tvTitle.setText(R.string.whatsapp_status);
        } else {
            tvTitle.setText(R.string.saved_whatsapp_status);
        }
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        Utils.changeTabsFont(mContext, tabs);
    }

    @Override
    public void initActions() {
        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PhotoFragment(), getString(R.string.whatsapp_photo));
        adapter.addFragment(new VideoFragment(), getString(R.string.whatsapp_video));
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onInterstitialAdClosed() {

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
    protected void onDestroy() {
        Glide.with(getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

}
