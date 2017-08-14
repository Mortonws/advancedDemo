package com.advanced.demo.emoji;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiBean {

    public List<Emoji> list;

    public static class Emoji implements Parcelable{
        public String content;
        public String id;
        public String tip;

        public Emoji() {
        }

        protected Emoji(Parcel in) {
            content = in.readString();
            id = in.readString();
            tip = in.readString();
        }

        public static final Creator<Emoji> CREATOR = new Creator<Emoji>() {
            @Override
            public Emoji createFromParcel(Parcel in) {
                return new Emoji(in);
            }

            @Override
            public Emoji[] newArray(int size) {
                return new Emoji[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(content);
            dest.writeString(id);
            dest.writeString(tip);
        }
    }
}
