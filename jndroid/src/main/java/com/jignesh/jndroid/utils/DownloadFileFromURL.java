package com.jignesh.jndroid.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Background Async Task to download file
 */
public class DownloadFileFromURL extends AsyncTask<String, String, Boolean> {


    private Context mContext;
    private ProgressDialog pDialog;
    private DownloadListener downloadListener;
    private boolean showDialog;

    public DownloadFileFromURL(Context mContext,boolean showDialog, DownloadListener downloadListener) {
        this.mContext = mContext;
        this.downloadListener = downloadListener;
        this.showDialog = showDialog;
    }

    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            showDialog(mContext);
        }
    }


    /**
     * Downloading file in background thread
     */
    @Override
    protected Boolean doInBackground(String... f_url) {
        int count;
        try {


            String filePath = f_url[0];
            String file_download_path = f_url[1];

            URL url = new URL(filePath);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(file_download_path);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();


            if (isCancelled()) {
                return false;
            }


            return true;


        } catch (Exception e) {
            Log.e("doInBackground: Error: ", e.getMessage());
        }

        return false;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        if (showDialog)
            pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(Boolean success) {
        // dismiss the dialog after the file was downloaded
        if (showDialog) {
            pDialog.dismiss();
        }
        downloadListener.onDownload(success);
    }

    private void showDialog(Context mContext) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setTitle("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public interface DownloadListener {
        public void onDownload(boolean success);
    }

}