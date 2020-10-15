package com.jignesh.jndroid.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.jignesh.jndroid.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    // request code
    public static final int REQUEST_MULTIPLE = 0;
    public static final int REQUEST_STORAGE = 1;
    public static final int REQUEST_CALENDAR = 2;
    public static final int REQUEST_CONTACTS = 3;
    public static final int REQUEST_LOCATION = 4;
    public static final int REQUEST_CALL_LOG = 5;
    public static final int REQUEST_SMS = 6;
    public static final int REQUEST_CAMERA = 7;
    public static final int REQUEST_GET_ACCOUNTS = 8;
    public static final int REQUEST_RECORD_AUDIO = 9;
    public static final int REQUEST_READ_PHONE_STATE = 10;
    public static final int REQUEST_READ_PHONE_NUMBERS = 11;
    public static final int REQUEST_CALL_PHONE = 12;
    public static final int REQUEST_ANSWER_PHONE_CALLS = 13;
    public static final int REQUEST_ADD_VOICEMAIL = 14;
    public static final int REQUEST_USE_SIP = 15;
    public static final int REQUEST_PROCESS_OUTGOING_CALLS = 16;
    public static final int REQUEST_BODY_SENSORS = 17;
    public static final int REQUEST_RECEIVE_WAP_PUSH = 18;
    public static final int REQUEST_RECEIVE_MMS = 19;

    // Group  permissions
    public static final String STORAGE = Manifest.permission_group.STORAGE;
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    public static final String CAMERA_GROUP = Manifest.permission_group.CAMERA;
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    public static final String PHONE = Manifest.permission_group.PHONE;
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    public static final String SMS = Manifest.permission_group.SMS;

    // permissions
    public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String READ_PHONE_NUMBERS = Manifest.permission.READ_PHONE_NUMBERS;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String ANSWER_PHONE_CALLS = Manifest.permission.ANSWER_PHONE_CALLS;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;

    // messages
    public static String FAILED_SINGLE = "Required Permission not granted";
    public static String FAILED_MULTIPLE = "Required Permissions not granted";

    /**
     * ToDo.. Check if permission is already exist
     *
     * @param mContext   The context
     * @param permission The name of the permission being checked.
     * @return {@link PackageManager#PERMISSION_GRANTED} if you have the
     * permission, or {@link PackageManager#PERMISSION_DENIED} if not.
     */
    public static boolean hasPermission(Context mContext, String permission) {
        return ActivityCompat.checkSelfPermission(mContext, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * ToDo.. Check if permissions are already exist
     *
     * @param mContext    The context
     * @param permissions The name of the permission being checked.
     * @return {@link PackageManager#PERMISSION_GRANTED} if you have the
     * permission, or {@link PackageManager#PERMISSION_DENIED} if not.
     */
    public static boolean hasPermission(Context mContext, String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            result = ContextCompat.checkSelfPermission(mContext, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        return listPermissionsNeeded.isEmpty();
    }

    /**
     * ToDo.. Requests permission to be granted to this application.
     * These permissions must be requested in your manifest, they should not be granted to your app,
     * and they should have protection level {@link android.content.pm.PermissionInfo
     * #PROTECTION_DANGEROUS dangerous}, regardless whether they are declared by
     * the platform or a third-party app.
     * <p>
     * Normal permissions {@link android.content.pm.PermissionInfo#PROTECTION_NORMAL}
     * are granted at install time if requested in the manifest. Signature permissions
     * {@link android.content.pm.PermissionInfo#PROTECTION_SIGNATURE} are granted at
     * install time if requested in the manifest and the signature of your app matches
     * the signature of the app declaring the permissions.
     * </p>
     *
     * @param mContext   The target activity content.
     * @param permission The requested permission. Must me non-null and not empty.
     * @param listener   Listener weather permission granted or not or permanently denied
     */
    public static void requestPermission(Context mContext, String[] permission,
                                         final PermissionListener listener) {
        Dexter.withActivity((Activity) mContext)
                .withPermissions(permission)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            listener.onGranted();
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            listener.onPermenantlyDenied();
                        } else {
                            listener.onDenied();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    /**
     * ToDo.. Show alert dialog if user denied permission
     *
     * @param mContext   The context
     * @param message    The message to display
     * @param okListener The okListener to handle user response
     */
    public static void showDialog(Context mContext, String message, DialogInterface
            .OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    /**
     * Alert dialog to navigate to app settings
     * to enable necessary permissions
     */
    public static void showPermissionsAlert(final Context mContext) {
        String titleText = mContext.getString(R.string.req_permission_title);

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan =
                new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimaryDark));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        AlertDialog dialog1 = new AlertDialog.Builder(mContext)
                .setTitle(ssBuilder)
                .setMessage(R.string.req_permission_msg)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openSettings(mContext);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
        TextView textView = dialog1.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/Montserrat-Regular" +
                ".ttf");
        textView.setTypeface(face);
    }

    /**
     * Open device app settings to allow user to enable permissions
     */
    public static void openSettings(Context context) {

        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) context).startActivityForResult(intent, 101);
        } catch (Exception e) {
            Log.i("TAG", "openSettings: " + e.toString());
        }


    }


    public interface PermissionListener {
        void onGranted();

        void onDenied();

        void onPermenantlyDenied();
    }
}
