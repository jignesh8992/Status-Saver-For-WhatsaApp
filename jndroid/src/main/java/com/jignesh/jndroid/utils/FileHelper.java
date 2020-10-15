package com.jignesh.jndroid.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Locale;

public class FileHelper {

    private static String TAG = "JNP__" + FileHelper.class.getSimpleName();

    /**
     * ToDo.. Return file size in B, KB, MB, GB format
     *
     * @param bytes The size to convert
     */
    @SuppressLint("DefaultLocale")
    public static String formatFileSize(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.2f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * ToDo.. Check File extension
     *
     * @param path      The path to check extension
     * @param extension The extension to check
     */
    public static boolean isExtension(String path, String extension) {
        String curr_extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        if (extension.equalsIgnoreCase(curr_extension)) {
            return true;
        }
        return false;
    }


    /**
     * ToDo.. Get name of file
     *
     * @param path The path
     * @return The name of path
     */
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * ToDo.. Load List of files from given directory path
     *
     * @param folder The folder to retrieve list of files
     */
    public static ArrayList<File> loadFiles(String folder) {
        ArrayList<File> pathList = new ArrayList<File>();
        String[] fileNames = null;
        File path = new File(folder);
        if (path.exists()) {
            fileNames = path.list();
        }
        if (fileNames != null) {
            for (int i = 0; i < fileNames.length; i++) {
                pathList.add(new File(path.getPath() + "/" + fileNames[i]));
            }
        }
        return pathList;
    }

    /**
     * ToDo.. Delete file by path
     *
     * @param path The path to delete
     */
    public static boolean delete(String path) {
        return new File(path).delete();
    }

    /**
     * ToDo.. Delete file by Uri
     *
     * @param mContext The Context
     * @param uri      The Uri to delete.
     */
    public static boolean delete(Context mContext, Uri uri) {
        return new File(getPath(mContext, uri)).delete();
    }


    /**
     * ToDo.. Copies one file into the other with the given paths.
     * In the event that the paths are the same, trying to copy one file to the other
     * will cause both files to become null.
     * Simply skipping this step if the paths are identical.
     */
    public static void copyFile(@NonNull String pathFrom, @NonNull String pathTo) throws
            IOException {
        if (pathFrom.equalsIgnoreCase(pathTo)) {
            return;
        }

        FileChannel outputChannel = null;
        FileChannel inputChannel = null;
        try {
            inputChannel = new FileInputStream(new File(pathFrom)).getChannel();
            outputChannel = new FileOutputStream(new File(pathTo)).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputChannel.close();
        } finally {
            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        }
    }


    /**
     * ToDo.. Get a file path from a Uri.
     * This will get the the path for Storage Access Framework Documents, as well as the _data
     * field for the MediaStore and other file-based ContentProviders.
     * Callers should check whether the path is local before assuming it represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author Jignehs
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id)) {
                    try {
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf
                                        (id));
                        return getDataColumn(context, contentUri, null, null);
                    } catch (NumberFormatException e) {
                        Log.i(TAG, e.getMessage());
                        return null;
                    }
                }

            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author Jignehs
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author Jignehs
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author Jignehs
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author Jignehs
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (IllegalArgumentException ex) {
            Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", ex
                    .getMessage()));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    public static void refreshGallery(Context mContext,String path){
        MediaScannerConnection.scanFile(mContext, new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("8992", "Scanned " + path);
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.parse(path); //out is your file you
            // saved/deleted/moved/copied
            mediaScanIntent.setData(contentUri);
            mContext.sendBroadcast(mediaScanIntent);
        } else {
            mContext.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }


        mContext. sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(path))));
    }

}
