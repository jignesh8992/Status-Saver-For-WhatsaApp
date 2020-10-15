package com.status.story.saver.downloader.free.aboutus;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.stepstone.aboutpage.AboutPage;
import com.stepstone.aboutpage.AboutPageUtils;
import com.stepstone.aboutpage.Element;

public class AboutActivity extends BaseActivity {


    public static final String KEY_VERSION = "version";

    private LinearLayout linearToolbar;
    private RelativeLayout realtiveToolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout relativeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

    }

    @Override
    protected Activity getContext() {
        return AboutActivity.this;
    }

    @Override
    public void initViews() {
        linearToolbar = findViewById(R.id.linear_toolbar);
        realtiveToolbar = findViewById(R.id.realtive_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        relativeContainer = findViewById(R.id.relative_container);
    }

    @Override
    public void initAds() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");
        String version = "App Version: " + getIntent().getStringExtra(KEY_VERSION);
        try {
            View aboutPage =
                    new AboutPage(this)
                            .isRTL(false)
                            .setImage(R.drawable.about_trans)
                            .addItem(new Element().setTitle(version))
                            .addGroup("Connect with us")
                            .addEmail("risingdeveloper8992@gmail.com")
                            .addPlayStore(getPackageName())
                        //    .addInstagram("risingdevelopers")
                            .addFacebook("risingdeveloper8992")
                            .addItem(AboutPageUtils.getCopyRightsElement(mContext))
                            .create();
            aboutPage.setBackgroundColor(getResources().getColor(R.color.white));
            relativeContainer.addView(aboutPage);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();

            View aboutPage =
                    new AboutPage(this)
                            .isRTL(false)
                            .setImage(R.drawable.about_trans)
                            .addItem(new Element().setTitle(version))
                            .addGroup("Connect with us")
                            .addEmail("risingdeveloper8992@gmail.com")
                            .addPlayStore(getPackageName())
                            .addItem(AboutPageUtils.getCopyRightsElement(mContext))
                            .create();
            aboutPage.setBackgroundColor(getResources().getColor(R.color.white));
            relativeContainer.addView(aboutPage);

        }
    }

    @Override
    public void initActions() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onInterstitialAdClosed() {

    }

}
