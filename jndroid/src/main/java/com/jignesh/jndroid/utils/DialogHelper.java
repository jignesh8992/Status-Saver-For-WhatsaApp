package com.jignesh.jndroid.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jignesh.jndroid.R;


public class DialogHelper {


    public static void showAlert(final Context mContext, String title, String msg, final onPositive positive) {

        String titleText = title;

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimaryDark));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog dialog1 = new AlertDialog.Builder(mContext).setTitle(ssBuilder).setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
        TextView textView = dialog1.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), Constant.font_regular);
        textView.setTypeface(face);


    }


    public static void showAlert(Context mContext, final onPositive positive) {
        //   final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String titleText = mContext.getString(R.string.dialog_title_warning);

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimaryDark));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog dialog1 = new AlertDialog.Builder(mContext).setTitle(ssBuilder).setMessage(R.string.dialog_confirmation).setPositiveButton(R.string.dialog_yes,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                positive.onYes();
            }
        }).setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialog1.dismiss();
            }
        }).show();
        TextView textView = dialog1.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), Constant.font_regular);
        textView.setTypeface(face);


    }

    public interface onPositive {
        public void onYes();
    }


    public static void showRateDialog(final Context mContext, final onPositive positive) {
        //   final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String titleText = mContext.getString(R.string.dialog_title_rate_app);

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimaryDark));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog dialog1 = new AlertDialog.Builder(mContext).setTitle(ssBuilder).setMessage(R.string.rate_dialog_message).setNegativeButton(R.string.dialog_exit,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialog1.dismiss();
                new SPHelper(mContext).save(SPHelper.APP_EXIT_COUNTER, 1);
                ((Activity) mContext).finishAffinity();

            }
        }).setPositiveButton(R.string.dialog_rate_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new SPHelper(mContext).save(SPHelper.APP_RATED, true);
                positive.onYes();

            }
        }).show();
        TextView textView = dialog1.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), Constant.font_regular);
        textView.setTypeface(face);


    }


    public static void showMoreAppAlert(Context mContext, final onPositive positive) {
        //   final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String titleText = mContext.getString(R.string.dialog_title_ads);

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimaryDark));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog dialog1 = new AlertDialog.Builder(mContext).setTitle(ssBuilder).setMessage(R.string.dialog_more).setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                positive.onYes();
            }
        }).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialog1.dismiss();
            }
        }).show();
        TextView textView = dialog1.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), Constant.font_regular);
        textView.setTypeface(face);


    }



}
