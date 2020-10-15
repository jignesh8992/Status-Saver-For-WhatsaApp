package com.jignesh.jndroid.utils;

public class ParcelableHelper {

    /**
    *  ToDo.. Model class
    */
    public void model(){

        /*import android.os.Parcel;
        import android.os.Parcelable;

        public class Name implements Parcelable {

            public static final Creator<jnp.example.com.androidexamples.Name> CREATOR = new Creator<jnp.example.com.androidexamples.Name>() {
                @Override
                public jnp.example.com.androidexamples.Name createFromParcel(Parcel in) {
                    return new jnp.example.com.androidexamples.Name(in);
                }

                @Override
                public jnp.example.com.androidexamples.Name[] newArray(int size) {
                    return new jnp.example.com.androidexamples.Name[size];
                }
            };

            // var
            String FirstName;
            String SureName;

            protected Name(Parcel in) {
                FirstName = in.readString();
                SureName = in.readString();
            }

            // default constructor
            public Name() {

            }

            public String getFirstName() {
                return FirstName;
            }

            public void setFirstName(String firstName) {
                FirstName = firstName;
            }

            public String getSureName() {
                return SureName;
            }

            public void setSureName(String sureName) {
                SureName = sureName;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(FirstName);
                dest.writeString(SureName);
            }
        }*/

    }

    public void SourceActivity(){

        /*ArrayList<Name> list=new ArrayList<>();

        Name name = new Name();
        name.setFirstName("Jignesh");
        name.setSureName("Patel");
        list.add(name);

        intent.putExtra("model",name);
        intent.putExtra("arraylist",list);*/
    }

    public void TargetActivity(){

        /*Name name = getIntent().getExtras().getParcelable("model");
        ArrayList<Name> list = getIntent().getParcelableArrayListExtra("arraylist");

        // from model
        String fname=name.getFirstName();
        String sname=name.getSureName();

        // from arraylist
        String fname=list.get(0).getFirstName();
        String sname=list.get(0).getSureName();*/
    }
}
