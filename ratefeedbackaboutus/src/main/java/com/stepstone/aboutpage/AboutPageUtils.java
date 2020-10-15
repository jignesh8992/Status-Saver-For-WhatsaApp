package com.stepstone.aboutpage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.stepstone.apprating.R;

import java.util.Calendar;
import java.util.List;

public class AboutPageUtils {

    static Boolean isAppInstalled(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(appName)) {
                installed = true;
                break;
            }
        }

        return installed;
    }

    static int getThemeAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

  public   static Element getCopyRightsElement(Context mContext) {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(mContext.getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}
