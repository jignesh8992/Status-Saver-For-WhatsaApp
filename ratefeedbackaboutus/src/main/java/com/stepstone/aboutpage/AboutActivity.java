package com.stepstone.aboutpage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.stepstone.apprating.R;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity {


    public static final String KEY_VERSION = "version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        String  version = "Version "+getIntent().getStringExtra(KEY_VERSION);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about_trans)
                .addItem(new Element().setTitle(version))
              //  .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("risingdeveloper8992@gmail.com")
            //    .addWebsite("http://medyo.github.io/")
                .addFacebook("risingdeveloper8992")
             //   .addTwitter("medyo80")
             //   .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.status.story.saver.downloader.free")
                .addInstagram("risingdevelopers")
            //    .addGitHub("jignesh8992")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
