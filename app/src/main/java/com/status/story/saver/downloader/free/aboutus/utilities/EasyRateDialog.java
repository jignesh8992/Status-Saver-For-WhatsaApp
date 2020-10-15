package com.status.story.saver.downloader.free.aboutus.utilities;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.status.story.saver.downloader.free.R;
import com.stepstone.apprating.AppRatingDialog;

public class EasyRateDialog {

    public static void show(Activity mContext){
        new  AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Exit")
                .setNumberOfStars(5)
                .setCommentInputEnabled(false)
              //  .setNoteDescriptions(listOf("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(4)
                .setTitle("Did you like this App?")
                .setDescription("Tap the numbers of stars would you give us.")
                .setStarColor(R.color.star2)
                .setNoteDescriptionTextColor(R.color.star2)
                .setTitleTextColor(R.color.black)
                .setDescriptionTextColor(R.color.colorPrimaryDark)
                .setCommentTextColor(R.color.colorPrimaryDark)
                .setCommentBackgroundColor(R.color.light)
                .setWindowAnimation(R.style.MyRateDialogAnimation)
                .setHint("Please write your ideas or tell us how we can improve our app for you")
                .setHintTextColor(R.color.colorPrimaryDark)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create((FragmentActivity) mContext)
                .show();
    }
}
