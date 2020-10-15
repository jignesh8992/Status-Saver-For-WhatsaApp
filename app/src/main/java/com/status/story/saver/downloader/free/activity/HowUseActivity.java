package com.status.story.saver.downloader.free.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.ads.NativeBannerAdsHelper;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.Constant;

public class HowUseActivity extends BaseActivity {

    private String TAG = HowUseActivity.class.getSimpleName();

    private LinearLayout linearToolbar;
    private RelativeLayout realtiveToolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout linearContainer;
    private TextView tvHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(HowUseActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_use);
    }

    @Override
    protected Activity getContext() {
        return HowUseActivity.this;
    }

    @Override
    public void initViews() {
        linearToolbar = findViewById(R.id.linear_toolbar);
        realtiveToolbar = findViewById(R.id.realtive_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        linearContainer = findViewById(R.id.linear_container);
        tvHint = findViewById(R.id.tv_hint);
    }

    @Override
    public void initAds() {


        if (new AdsManager(mContext).isNeedToShowAds()) {
            try {
                NativeBannerAdsHelper.getInstance().loadNativeBanner(mContext);
            } catch (Exception e) {
                Log.e(TAG_ADS, "Ads_Native_Load_Error: " + e.getMessage());
            }
        } else {
            manageRemoveAds();
        }
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

        int helpType = getIntent().getIntExtra("help_type", Constant.TYPE_HELP);
        String helpMessage = getString(R.string.how_to_use_status_saver);
        String title = getString(R.string.how_to_use_status_saver_title);
        if (helpType == Constant.HELP_STATUS_SAVER) {
            helpMessage = getString(R.string.how_to_use_status_saver);
            title = getString(R.string.how_to_use_status_saver_title);
        } else {
            title = getString(R.string.how_to_use_whatsapp_direct_title);
            helpMessage = getString(R.string.how_to_use_whatsapp_direct);
        }
        tvTitle.setText(title);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvHint.setText(Html.fromHtml(helpMessage, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvHint.setText(Html.fromHtml(helpMessage));
        }
        tvHint.setMovementMethod(new ScrollingMovementMethod());
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


    @Override
    public void onInterstitialAdClosed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!new AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds();
        }
    }


    private void manageRemoveAds() {
        findViewById(R.id.rel_native_banner_ad).setVisibility(View.GONE);
    }

}
