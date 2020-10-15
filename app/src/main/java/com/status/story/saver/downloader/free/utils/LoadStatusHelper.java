package com.status.story.saver.downloader.free.utils;

import androidx.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LoadStatusHelper {

    private static String TAG = "JNP__" + LoadStatusHelper.class.getSimpleName();


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
     * ToDo.. Copies one file into the other with the given paths.
     * In the event that the paths are the same, trying to copy one file to the other
     * will cause both files to become null.
     * Simply skipping this step if the paths are identical.
     *
     * @param pathFrom The source file path from file will be copy
     * @param pathTo   The destination path where file will be copied
     */
    public static String copyFile(@NonNull String pathFrom, @NonNull String pathTo) {
        if (pathFrom.equalsIgnoreCase(pathTo)) {
            return pathFrom;
        }
        String fileName = getFileName(pathFrom);
        InputStream in = null;
        OutputStream out = null;
        try {

            File dir = new File(pathTo);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            in = new FileInputStream(pathFrom);


            out = new FileOutputStream(pathTo + File.separator + fileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage()+"");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage()+"");
        }

        return pathTo + File.separator + fileName;
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


    public static ArrayList<File> getAllStatuses() {
        String folder_path = Constant.folder;
        String folder_path2 = Constant.folder2;
        String folder_path3 = Constant.folder3;
        String folder_path4 = Constant.folder4;
        String folder_path5 = Constant.folder5;
        String folder_path6 = Constant.folder6;
        String folder_path7 = Constant.folder7;
        String folder_path8 = Constant.folder8;
        String folder_path9 = Constant.folder9;
        ArrayList<File> list = LoadStatusHelper.loadFiles(folder_path);
        ArrayList<File> list2 = LoadStatusHelper.loadFiles(folder_path2);
        ArrayList<File> list3 = LoadStatusHelper.loadFiles(folder_path3);
        ArrayList<File> list4 = LoadStatusHelper.loadFiles(folder_path4);
        ArrayList<File> list5 = LoadStatusHelper.loadFiles(folder_path5);
        ArrayList<File> list6 = LoadStatusHelper.loadFiles(folder_path6);
        ArrayList<File> list7 = LoadStatusHelper.loadFiles(folder_path7);
        ArrayList<File> list8 = LoadStatusHelper.loadFiles(folder_path8);
        ArrayList<File> list9 = LoadStatusHelper.loadFiles(folder_path9);
        list.addAll(list2);
        list.addAll(list3);
        list.addAll(list4);
        list.addAll(list5);
        list.addAll(list6);
        list.addAll(list7);
        list.addAll(list8);
        list.addAll(list9);
        return list;
    }
}
