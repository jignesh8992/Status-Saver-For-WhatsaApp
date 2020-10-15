package com.jignesh.jndroid;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;


public class JndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont(this, "DEFAULT", "fonts/Montserrat-Medium.ttf");
        setDefaultFont(this, "MONOSPACE", "fonts/Montserrat-Medium.ttf");
        setDefaultFont(this, "SERIF", "fonts/Montserrat-Regular.ttf");
        initImageLoader(getApplicationContext());
    }


    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static void loadFromSdcard(String imageUri, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("file:///" + imageUri, imageView);
    }

    public static void loadFromAssets(String imageUri, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://" + imageUri, imageView);
    }

    public static void loadFromDrawable(int resId, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + resId, imageView);
    }

    public static void loadFromWeb(String imageUri, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(imageUri, imageView);
    }

    public static void loadFromCP(String imageUri, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("content://" + imageUri, imageView);
    }
}