package com.stepstone.aboutpage;

import android.content.Context;
import android.content.Intent;

/**
 * Created by R Ankit on 28-10-2016.
 */

public class EasyAbout {

    private Context context;
    private String version;


    public EasyAbout(Builder builder) {
        this.version = builder.emailId;
        this.context = builder.context;
    }

    public static class Builder {
        private Context context;
        private String emailId;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder withVersion(String email) {
            this.emailId = email;
            return this;
        }

        public EasyAbout build() {
            return new EasyAbout(this);
        }

    }

    public void start() {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra("version", version);
        context.startActivity(intent);

    }

}
