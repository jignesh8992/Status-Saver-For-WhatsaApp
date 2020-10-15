package com.status.story.saver.downloader.free.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.jignesh.jndroid.utils.DialogHelper;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.ads.NativeBannerAdsHelper;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.ClickShrinkEffect;
import com.status.story.saver.downloader.free.utils.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DirectChatActivity extends BaseActivity {

    private LinearLayout linearToolbar;
    private RelativeLayout realtiveToolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivInfo;
    private TextView tvInfo;
    private CountryCodePicker cppCountry;
    private EditText etMsg;
    private EditText et_phone;
    private ImageView ivSend;
    private ImageView ivSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_chat);
    }

    @Override
    protected Activity getContext() {
        return DirectChatActivity.this;
    }

    @Override
    public void initViews() {
        linearToolbar = findViewById(R.id.linear_toolbar);
        realtiveToolbar = findViewById(R.id.realtive_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        ivInfo = findViewById(R.id.iv_info);
        tvInfo = findViewById(R.id.tv_info);
        cppCountry = findViewById(R.id.cpp_country);
        etMsg = findViewById(R.id.et_msg);
        et_phone = findViewById(R.id.et_phone);
        ivSend = findViewById(R.id.iv_send);
        ivSave = findViewById(R.id.iv_save);
        cppCountry.registerCarrierNumberEditText(et_phone);
    }

    @Override
    public void initAds() {

        try {
            if (new AdsManager(mContext).isNeedToShowAds()) {
                NativeBannerAdsHelper.getInstance().loadNativeBanner(mContext);
            } else {
                manageRemoveAds();
            }
        } catch (Exception e) {
            Log.e(TAG_ADS, "Ads_Native_Load_Error: " + e.getMessage());
        }


    }

    @Override
    public void initUI() {

        setHeightWidth(ivSend, 415, 96);
        setHeightWidth(ivSave, 415, 96);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initActions() {


        new ClickShrinkEffect(ivSend);
        new ClickShrinkEffect(ivSave);


        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });

        ivSave.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                SaveContact();
            }
        });

        ivSend.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (et_phone.getText().toString().isEmpty()) {
                    DialogHelper.showAlert(mContext, getResources().getString(R.string.empty_number), getResources().getString(R.string.enter_number), new DialogHelper.onPositive() {
                        @Override
                        public void onYes() {

                        }
                    });
                } else {
                    sendWhatsappDirectMessage();
                }
            }
        });

        ivInfo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                Intent intent = new Intent(mContext, HowUseActivity.class);
                intent.putExtra("help_type", Constant.HELP_DIRECT_MESSAGE);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onInterstitialAdClosed() {

    }


    public void SaveContact() {
        if (et_phone.getText().toString().isEmpty()) {
            DialogHelper.showAlert(mContext, getResources().getString(R.string.empty_number), getResources().getString(R.string.enter_number), new DialogHelper.onPositive() {
                @Override
                public void onYes() {

                }
            });
        } else {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, cppCountry.getFullNumberWithPlus());
            startActivity(intent);
        }

    }


    public void sendWhatsappDirectMessage() {
        try {
            String fullNumber = cppCountry.getFullNumber();
            String textMsg = etMsg.getText().toString();
            StringBuilder sb = new StringBuilder();
            sb.append("whatsapp://send/?text=");
            sb.append(URLEncoder.encode(textMsg, "UTF-8"));
            sb.append("&phone=");
            sb.append(fullNumber);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(mContext, R.string.update_whatsapp, Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException unused2) {
            Toast.makeText(mContext, R.string.text_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!new AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds();
        }
    }


    private void manageRemoveAds() {
        findViewById(R.id.relative_ad_view).setVisibility(View.GONE);
    }
}

