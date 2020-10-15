package com.jignesh.jndroid.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtensionValidator {
    public static Pattern pattern;
    public static Matcher matcher;
    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|jpeg))$)";
    public static final String VIDEO_PATTERN = "([^\\s]+(\\.(?i)(mp4|3gp|flv|avi))$)";


    /**
     * Validate image with regular expression
     *
     * @param path path for validation
     * @return true valid image, false invalid image
     */
    public static boolean isImage(String path) {
        pattern = Pattern.compile(IMAGE_PATTERN);
        matcher = pattern.matcher(path);
        return matcher.matches();

    }

    /**
     * Validate video with regular expression
     *
     * @param path path for validation
     * @return true valid video, false invalid video
     */
    public static boolean isVideo(String path) {
        pattern = Pattern.compile(VIDEO_PATTERN);
        matcher = pattern.matcher(path);
        return matcher.matches();

    }
}