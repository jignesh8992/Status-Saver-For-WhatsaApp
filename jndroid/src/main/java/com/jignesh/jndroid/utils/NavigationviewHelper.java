package com.jignesh.jndroid.utils;

public class NavigationviewHelper {


    // .xml
   /* <android.support.design.widget.NavigationView
    android:id="@+id/nav_view"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_gravity="start"
    android:background="@color/colorPrimary"
    android:fitsSystemWindows="true"
    android:theme="@style/selected_item"
    app:headerLayout="@layout/nav_header_main"
    app:itemIconTint="@color/menu_text_color"
    app:itemTextColor="@color/menu_text_color"
    app:menu="@menu/activity_main_drawer" />*/



    /*
     * ToDo.. Change text color
     */
    // res/color/menu_text_color.xml
    /*<?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:color="your color" android:state_checked="true" />
        <item android:color="your color" android:state_checked="false"/>
    </selector>*/



    /*
     * ToDo.. More customization
     */
    // res/style.xml
    /*<style name="selected_item" parent="ThemeOverlay.AppCompat.Light">
        <item name="colorControlHighlight">your color</item> // selected item background color
        <item name="android:listDivider">your drawable</item> // divider
    </style>*/



    /*
     * ToDo.. Divider spacing
     */
    // dimen.xml
   /* <dimen  name="design_navigation_separator_vertical_padding">25dp</dimen> // top-bottom padding
	<dimen  name="design_navigation_separator_horizontal_padding">15dp</dimen> // left-right
	padding */

   

    /*
     * ToDo.. Icon Size
     */
    //dimen.xml
    /* <dimen name="design_navigation_icon_size" tools:override="true">40dp</dimen>*/

}
