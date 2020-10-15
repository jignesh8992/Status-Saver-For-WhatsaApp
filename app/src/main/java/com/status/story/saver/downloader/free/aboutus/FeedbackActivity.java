package com.status.story.saver.downloader.free.aboutus;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.jignesh.jndroid.utils.BitmapHelper;
import com.jignesh.jndroid.utils.ChooserHelper;
import com.jignesh.jndroid.utils.FileHelper;
import com.jignesh.jndroid.utils.Permission;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.shadow.ShadowView;
import com.status.story.saver.downloader.free.utils.ClickShrinkEffect;
import com.stepstone.easy_feedback.components.DeviceInfo;
import com.stepstone.easy_feedback.components.FeedbackUtils;
import com.stepstone.easy_feedback.components.FileUtils;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {


    private int PICK_IMAGE_REQUEST = 125;
    private String realPath;

    // Required Permissions
    private String[] permissions = {Permission.READ_STORAGE, Permission.WRITE_STORAGE};

    private LinearLayout linearToolbar;
    private RelativeLayout realtiveToolbar;
    private ShadowView sv_SelectImage;
    private ImageView ivBack;
    private TextView tvTitle;
    private EditText editTextSubject;
    private EditText editText;
    private ImageView ivSelectImage;
    private TextView infoLegal;
    private ImageView ivSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected Activity getContext() {
        return FeedbackActivity.this;
    }

    @Override
    public void initViews() {

        linearToolbar = findViewById(R.id.linear_toolbar);
        realtiveToolbar = findViewById(R.id.realtive_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        sv_SelectImage = findViewById(R.id.sv_select_image);
        editTextSubject = findViewById(R.id.editTextSubject);
        editText = findViewById(R.id.editText);
        ivSelectImage = findViewById(R.id.iv_select_image);
        infoLegal = findViewById(R.id.info_legal);
        ivSend = findViewById(R.id.iv_send);
    }

    @Override
    public void initAds() {

    }

    @Override
    public void initUI() {
        setHeightWidth(ivSend,715,103);
    }

    @Override
    public void initData() {
        CharSequence infoFeedbackStart = getResources().getString(R.string.info_fedback_legal_start);
        SpannableString deviceInfo = new SpannableString(getResources().getString(R.string.info_fedback_legal_system_info));
        deviceInfo.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                String titleText=getString(R.string.info_fedback_legal_system_info);
                // Initialize a new foreground color span instance
                ForegroundColorSpan foregroundColorSpan =
                        new ForegroundColorSpan(mContext.getResources().getColor(com.jignesh.jndroid.R.color.colorPrimaryDark));

                // Initialize a new spannable string builder instance
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                // Apply the text color span
                ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                new AlertDialog.Builder(FeedbackActivity.this).setTitle(ssBuilder).setMessage(DeviceInfo.getAllDeviceInfo(mContext, false)).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }, 0, deviceInfo.length(), 0);
        CharSequence infoFeedbackAnd = getResources().getString(R.string.info_fedback_legal_and);
        SpannableString systemLog = new SpannableString(getResources().getString(R.string.info_fedback_legal_log_data));
        systemLog.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                String titleText=getString(R.string.info_fedback_legal_log_data);
                // Initialize a new foreground color span instance
                ForegroundColorSpan foregroundColorSpan =
                        new ForegroundColorSpan(mContext.getResources().getColor(com.jignesh.jndroid.R.color.colorPrimaryDark));

                // Initialize a new spannable string builder instance
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                // Apply the text color span
                ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                new AlertDialog.Builder(FeedbackActivity.this).setTitle(ssBuilder).setMessage(FeedbackUtils.LOG_TO_STRING).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }, 0, systemLog.length(), 0);
        CharSequence infoFeedbackEnd = getResources().getString(R.string.info_fedback_legal_will_be_sent,
                FeedbackUtils.getAppLabel(mContext));
        Spanned finalLegal = (Spanned) TextUtils.concat(infoFeedbackStart, deviceInfo, infoFeedbackAnd, systemLog," ", infoFeedbackEnd);


        infoLegal.setText(finalLegal);
        infoLegal.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initActions() {
        new ClickShrinkEffect(ivSend);
        ivSend.setOnClickListener(this);
        sv_SelectImage.setOnClickListener(this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    realPath = FileHelper.getPath(mContext, uri);
                   Glide.with(mContext).load(realPath).into(ivSelectImage);
                    Toast.makeText(this, getString(R.string.click_again), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(this, getString(R.string.select_image_cancel), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void checkPermissions() {

        Permission.requestPermission(mContext, permissions, new Permission.PermissionListener() {
            @Override
            public void onGranted() {
                ChooserHelper.chooseImage(mContext, PICK_IMAGE_REQUEST);
            }

            @Override
            public void onDenied() {
                Toast.makeText(mContext, Permission.FAILED_MULTIPLE, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermenantlyDenied() {
                Permission.showPermissionsAlert(mContext);
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_send:
                String subject = editTextSubject.getText().toString();
                if (subject.trim().isEmpty()) {
                    editTextSubject.setError(getString(R.string.please_write));
                    return;
                }
                String suggestion = editText.getText().toString();
                if (suggestion.trim().length() > 0) {
                    FeedbackUtils.sendEmail(mContext, subject, suggestion, realPath);
                    finish();
                } else editText.setError(getString(R.string.please_write));
                break;
            case R.id.sv_select_image:
                checkPermissions();
                break;
        }


    }


}
