package com.stepstone.easy_feedback;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.stepstone.apprating.R;
import com.stepstone.easy_feedback.components.DeviceInfo;
import com.stepstone.easy_feedback.components.FileUtils;
import com.stepstone.easy_feedback.components.FeedbackUtils;


/**
 * Created by R Ankit on 28-10-2016.
 */

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_WITH_INFO = "with_info";
    public static final String KEY_EMAIL = "email";
    private final int REQUEST_APP_SETTINGS = 321;
    private final int REQUEST_PERMISSIONS = 123;
    private EditText editText, editTextSubject;
    private boolean withInfo =true;
    private int PICK_IMAGE_REQUEST = 125;
    private String realPath;
    private ImageView selectedImageView;
    private LinearLayout selectContainer;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
        mContext=FeedbackActivity.this;
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


    }


    private void init() {

        editText = findViewById(R.id.editText);
        editTextSubject = findViewById(R.id.editTextSubject);

        TextView info = findViewById(R.id.info_legal);
        FrameLayout selectImage = findViewById(R.id.selectImage);
        Button submitSuggestion = findViewById(R.id.iv_send);
        selectedImageView = findViewById(R.id.iv_select_image);
        selectContainer = findViewById(R.id.selectContainer);

        submitSuggestion.setOnClickListener(this);
        selectImage.setOnClickListener(this);

        withInfo = getIntent().getBooleanExtra(KEY_WITH_INFO, false);



        if (withInfo) {

            CharSequence infoFeedbackStart =
                    getResources().getString(R.string.info_fedback_legal_start);
            SpannableString deviceInfo =
                    new SpannableString(getResources().getString(R.string.info_fedback_legal_system_info));
            deviceInfo.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    new AlertDialog.Builder(FeedbackActivity.this)
                            .setTitle(R.string.info_fedback_legal_system_info)
                            .setMessage(DeviceInfo.getAllDeviceInfo(mContext, false))
                            .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }, 0, deviceInfo.length(), 0);
            CharSequence infoFeedbackAnd =
                    getResources().getString(R.string.info_fedback_legal_and);
            SpannableString systemLog =
                    new SpannableString(getResources().getString(R.string.info_fedback_legal_log_data));
            systemLog.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    new AlertDialog.Builder(FeedbackActivity.this)
                            .setTitle(R.string.info_fedback_legal_log_data)
                            .setMessage(FeedbackUtils.LOG_TO_STRING)
                            .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }, 0, systemLog.length(), 0);
            CharSequence infoFeedbackEnd =
                    getResources().getString(R.string.info_fedback_legal_will_be_sent,
                            FeedbackUtils.getAppLabel(mContext));
            Spanned finalLegal = (Spanned) TextUtils.concat(infoFeedbackStart, deviceInfo,
                    infoFeedbackAnd, systemLog, infoFeedbackEnd);


            info.setText(finalLegal);
            info.setMovementMethod(LinkMovementMethod.getInstance());

        } else
            info.setVisibility(View.GONE);

    }


    public void selectImage() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            int hasWriteContactsPermission =
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
                return;

            } else
                //already granted
                selectPicture();


        } else {
            //normal process
            selectPicture();
        }


    }

    private void selectPicture() {

        realPath = null;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.select_picture_title)), PICK_IMAGE_REQUEST);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.Ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSIONS:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    selectPicture();

                } else {
                    // Permission Denied
                    showMessageOKCancel("You need to allow access to SD card to select images.",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    goToSettings();

                                }
                            });

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }


    private void goToSettings() {

        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_APP_SETTINGS) {

            if (hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                //Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
                selectPicture();

            } else {

                showMessageOKCancel("You need to allow access to SD card to select images.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                goToSettings();

                            }
                        });

            }
        } else if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {

            realPath = FileUtils.getPath(this, data.getData());

            selectedImageView.setImageBitmap(FileUtils.decodeSampledBitmap(realPath,
                    selectedImageView.getWidth(), selectedImageView.getHeight()));

            selectContainer.setVisibility(View.GONE);

            Toast.makeText(this, getString(R.string.click_again), Toast.LENGTH_SHORT).show();


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean hasPermissions(@NonNull String... permissions) {
        for (String permission : permissions)
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                    permission))
                return false;
        return true;
    }







    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.iv_send) {

            String subject = editTextSubject.getText().toString();
            if (subject.trim().isEmpty()) {
                editTextSubject.setError(getString(R.string.please_write));
                return;
            }


            String suggestion = editText.getText().toString();
            if (suggestion.trim().length() > 0) {
                FeedbackUtils.sendEmail(mContext,subject, suggestion,realPath);
                finish();
            } else
                editText.setError(getString(R.string.please_write));

        } else if (view.getId() == R.id.selectImage)
            selectImage();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }




}
